package com.yt.retrievalartifact.config;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
@Getter
@ToString
public class HandlerPath {
    //包含的目录
    private Set<String >  includePath=new HashSet<>();

    //排除的目录
    private Set<String >  excludePath=new HashSet<>();

    private HandlerPath(){

    }

    /**
     * 要包含的目录
     * @param path
     */
    public void addIncludePath(String path){
        this.includePath.add(path);
    }

    /**
     * 要排除的目录
     * @param path
     */
    public  void addExcludePath(String path){
        this.excludePath.add(path);
    }

    public static HandlerPath getDefaultHandlPath(){
        //CDEF
        //Windows Program files排除掉
        HandlerPath handlerPath=new HandlerPath();
        //返回盘符根目录
        Iterable<Path> paths=FileSystems.getDefault().getRootDirectories();
      //默认要包含的目录和构建索引时要处理的路径
        paths.forEach(path -> {
            handlerPath.addIncludePath(path.toString());
            handlerPath.addExcludePath(path.toString());
        });
        //默认要排除的目录及构建索引时不需要处理的路径
        //Windows Linux
        String systemName=System.getProperty("os.name");
        if(systemName.contains("Windows")){
            //windows
            handlerPath.addExcludePath("C:\\Windows");
            handlerPath.addExcludePath("C:\\Program Files");
            handlerPath.addExcludePath("C:\\Program Files(x86)");
            handlerPath.addExcludePath("C:\\ProgramData");
        }else{
            //linux
            handlerPath.addExcludePath("/root");
            handlerPath.addExcludePath("/tmp");
        }
        return handlerPath;
    }

    public static void main(String[] args) {
        System.out.println(HandlerPath.getDefaultHandlPath());
    }
}
