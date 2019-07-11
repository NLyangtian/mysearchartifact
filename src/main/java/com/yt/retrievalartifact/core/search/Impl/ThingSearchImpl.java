package com.yt.retrievalartifact.core.search.Impl;

import com.yt.retrievalartifact.core.dao.FileIndexDao;
import com.yt.retrievalartifact.core.intercepter.impl.ThingsClearIntercepter;
import com.yt.retrievalartifact.core.model.Condition;
import com.yt.retrievalartifact.core.model.Things;
import com.yt.retrievalartifact.core.search.ThingSearch;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ThingSearchImpl implements ThingSearch {
    private  final FileIndexDao fileIndexDao;
    private  final ThingsClearIntercepter intercepter;
    private  final Queue<Things> thingsqueue=new ArrayBlockingQueue<>(1024);


    public ThingSearchImpl(FileIndexDao fileIndexDao) {
        this.fileIndexDao = fileIndexDao;
        this.intercepter=new ThingsClearIntercepter(this.fileIndexDao,thingsqueue);
        this.backgroundCleanThread();
    }

    @Override
    public List<Things> search(Condition condition) {
        //此处需要依赖数据库检索
     //如果本地文件系统将将文件删除，数据库中仍然存储到索引信息，词汇如果查询结果存在已经在本地文件
        //系统中删除的文件，那么需要在数据库中清除掉该文件的索引信息
        List<Things> things=this.fileIndexDao.query(condition);
        Iterator<Things> iterator=things.iterator();
        while(iterator.hasNext()){
            Things thing=iterator.next();
            File file=new File(thing.getPath());
            //如果文件不存在，则删除
            if(!file.exists()){
                //删除文件系统中不存在的文件
                iterator.remove();
                this.thingsqueue.add(thing);

//                //删除数据库中的索引
//                intercepter.apply(thing);
            }
        }
        return things;
    }

    //后台清理
    private  void backgroundCleanThread(){
        Thread thread=new Thread(this.intercepter);
        thread.setName("Thread-Clean");
        thread.setDaemon(true);
        thread.start();
    }
}
