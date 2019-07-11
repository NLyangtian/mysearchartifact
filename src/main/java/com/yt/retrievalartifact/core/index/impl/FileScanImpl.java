package com.yt.retrievalartifact.core.index.impl;

import com.yt.retrievalartifact.config.EverythingConfig;
import com.yt.retrievalartifact.core.index.FileScan;
import com.yt.retrievalartifact.core.intercepter.FileInterceptor;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.Set;

public class FileScanImpl  implements FileScan {

    private  final LinkedList<FileInterceptor> interceptors=new LinkedList<>();
    private EverythingConfig config= EverythingConfig.getInstance();
    @Override
    public void interceptor(FileInterceptor interceptor) {
        this.interceptors.add(interceptor);
    }

    @Override
    public void index(String path) {
        Set<String > excludePaths=config.getHandlerPath().getExcludePath();
        //判断所给出的路径有没有包含在内
        //判断APath是否在B Path中
        for(String excludePath :excludePaths){
            if(path.startsWith(excludePath)){
                return;
            }
        }
        File file=new File(path);
        //如果是一个目录，就递归索引一下
        if(file.isDirectory()){
            File[] files=file.listFiles();
            if(file!=null){
                for(File f:files){
                    index(f.getAbsolutePath());
                }
            }
        }

        //拦截每个文件对象
        for(FileInterceptor interceptor:this.interceptors){
            interceptor.apply(file);
        }

    }

}
