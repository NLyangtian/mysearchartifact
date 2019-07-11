package com.yt.retrievalartifact.core.index;

import com.yt.retrievalartifact.core.dao.DataSourceFactory;
import com.yt.retrievalartifact.core.dao.FileIndexDao;
import com.yt.retrievalartifact.core.dao.FileIndexImpl.FileIndexDaoImpl;
import com.yt.retrievalartifact.core.index.impl.FileScanImpl;
import com.yt.retrievalartifact.core.intercepter.FileInterceptor;
import com.yt.retrievalartifact.core.intercepter.impl.FileIndex;
import com.yt.retrievalartifact.core.intercepter.impl.FilePrintInterceptorImpl;

import javax.sql.DataSource;

//用来做文件索引，将本地文件路径变成文件对象
public interface FileScan {
    //将文件路径->文件对象->Things对象->存储到数据库中

    /**
     * 将指定path路径下的所有目录和文件以及子目录和文件递归扫描索引
     * 到数据库
     * @param path
     */
    void index(String  path);

    /**
     * 文件扫描接口增加拦截器对象
     * @param interceptor
     */
    void interceptor(FileInterceptor interceptor);


}
