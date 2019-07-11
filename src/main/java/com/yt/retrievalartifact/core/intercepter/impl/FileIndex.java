package com.yt.retrievalartifact.core.intercepter.impl;

import com.yt.retrievalartifact.core.common.FileConvertThing;
import com.yt.retrievalartifact.core.dao.FileIndexDao;
import com.yt.retrievalartifact.core.intercepter.FileInterceptor;
import com.yt.retrievalartifact.core.model.Things;

import java.io.File;

/**
 * 将File转换为Things然后写入数据库
 */
public class FileIndex implements FileInterceptor {
    private  final FileIndexDao fileIndexDao;

    public FileIndex(FileIndexDao fileIndexDao) {
        this.fileIndexDao = fileIndexDao;
    }

    //打印、转换、写入
    @Override
    public void apply(File file) {
        Things things=FileConvertThing.convert(file);
        this.fileIndexDao.insert(things);
    }
}
