package com.yt.retrievalartifact.core;

import com.yt.retrievalartifact.config.EverythingConfig;
import com.yt.retrievalartifact.config.HandlerPath;
import com.yt.retrievalartifact.core.dao.DataSourceFactory;
import com.yt.retrievalartifact.core.dao.FileIndexDao;
import com.yt.retrievalartifact.core.dao.FileIndexImpl.FileIndexDaoImpl;
import com.yt.retrievalartifact.core.index.FileScan;
import com.yt.retrievalartifact.core.index.impl.FileScanImpl;
import com.yt.retrievalartifact.core.intercepter.impl.FileIndex;
import com.yt.retrievalartifact.core.intercepter.impl.FilePrintInterceptorImpl;
import com.yt.retrievalartifact.core.model.Condition;
import com.yt.retrievalartifact.core.model.Things;
import com.yt.retrievalartifact.core.search.Impl.ThingSearchImpl;
import com.yt.retrievalartifact.core.search.ThingSearch;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 核心的统一调度器
 * 1.如何做索引
 *
 * 2.如何做检索
 *
 * 配置：索引模块，检索模块，拦截器模块组合调度
 */
public class EverythingMannager {

    private  static volatile  EverythingMannager mannager;
    //业务层
    private FileScan fileScan;
    private ThingSearch thingSearch;

    //数据库访问层
    private FileIndexDao fileIndexDao;

    //线程池的执行器
    private final ExecutorService executorService=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
     private  EverythingConfig config=EverythingConfig.getInstance();

    private  EverythingMannager(){
        this.fileScan=new FileScanImpl();
        this.fileIndexDao=new FileIndexDaoImpl(DataSourceFactory.getInstance());
        //打印索引信息的拦截器
        //this.fileScan.interceptor(new FilePrintInterceptorImpl());
        //索引信息写数据库的拦截器
        this.fileScan.interceptor(new FileIndex(this.fileIndexDao));

        this.thingSearch=new ThingSearchImpl(this.fileIndexDao);
    }

    public static  EverythingMannager getInstance(){
        if(mannager==null){
            synchronized (EverythingMannager.class){
                if(mannager==null){
                    mannager=new EverythingMannager();
                }
            }
        }
        return mannager;
    }

    /**
     * 构建索引
     */
    public void buildIndex(){
        //建立索引
        DataSourceFactory.databaseInit();
        HandlerPath handlerPath=config.getHandlerPath();
        Set<String > includePaths= handlerPath.getIncludePath();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Builde Index Started ...");
                final CountDownLatch countDownLatch = new CountDownLatch(includePaths.size());
                for (String path : includePaths) {
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            fileScan.index(path);
                            countDownLatch.countDown();
                        }
                    });

                }
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Build Index complete...");
                }


        }).start();


    }

    /**
     * 检索
     * @param condition
     */
    public List<Things> search(Condition condition){
        //condition 用户提供的的是：name file_type
        //limit orderBy
        condition.setLimit(config.getMaxReturn());
        condition.setOrderByDepthAsc(!config.getOrderByDepthDesc());
        return this.thingSearch.search(condition);
    }

    /**
     * 帮助
     */
    public  void help(){
        /**
         * 命令列表：
         * 退出：quit
         * 帮助：help
         * 索引：index
         * 搜索：search <name> [<file-Type> img | doc | bin | archive | other]
         */
        System.out.println("命令列表：");
        System.out.println("退出：quit");
        System.out.println("帮助：help");
        System.out.println("索引：index");
        System.out.println("搜索：search  <name>  [<file-Type> img | doc | bin | archive | other]");


    }

    /**
     * 退出
     */
    public void quit(){
        System.out.println("期待您的下次使用，bye !");
        System.exit(0);
    }
}
