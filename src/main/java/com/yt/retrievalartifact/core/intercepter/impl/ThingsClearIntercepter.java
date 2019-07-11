package com.yt.retrievalartifact.core.intercepter.impl;

import com.yt.retrievalartifact.core.dao.FileIndexDao;
import com.yt.retrievalartifact.core.intercepter.ThingInterceptor;
import com.yt.retrievalartifact.core.model.Things;
import com.yt.retrievalartifact.core.search.Impl.ThingSearchImpl;

import java.util.Queue;

public class ThingsClearIntercepter implements ThingInterceptor,Runnable{
    private  final FileIndexDao fileIndexDao;
    private  final Queue<Things> thingsQueue;
    public ThingsClearIntercepter(FileIndexDao fileIndexDao, Queue<Things> thingsQueue) {
        this.fileIndexDao = fileIndexDao;
        this.thingsQueue=thingsQueue;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Things things=this.thingsQueue.poll();
            if(things!=null){
                this.apply(things);
            }
        }
    }

    @Override
    public void apply(Things things) {
        this.fileIndexDao.delete(things);
    }
}
