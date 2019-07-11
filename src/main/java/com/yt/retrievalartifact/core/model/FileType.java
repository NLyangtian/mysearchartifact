package com.yt.retrievalartifact.core.model;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 对文件类型进行模型抽象：
 *  FileType：表示文件的扩展名的归类
 */
public enum FileType {
    IMG("jpg","jpeg","png","bmp","gif"),
    DOC("doc","docx","pdf","ppt","pptx","xsl","txt"),
    BIN("exe","jar","sh","msi"),
    ARCHIVE("zip","rar"),
    OTHER("*");


    private Set<String> extend=new HashSet<>();

    FileType(String ... extend){
        this.extend.addAll(Arrays.asList(extend));
    }

    //根据扩展名查找文件类型
    public static FileType lookUpByExtend(String extend){
        for(FileType fileType:FileType.values()){
            if(fileType.extend.contains(extend)){
                return fileType;
            }
        }
        return FileType.OTHER;
    }

    //根据文件名查找文件
    public static FileType lookUpByName(String name){
        for(FileType fileType:FileType.values()){
            if(fileType.name().equals(name)){
                return fileType;
            }
        }
        return FileType.OTHER;
    }

//    public static void main(String[] args) {
//        System.out.println(FileType.lookUpByExtend("exe"));//BIN
//        System.out.println(FileType.lookUpByExtend("md"));//OTHER
//        System.out.println(FileType.lookUpByName("BIN"));//BIN
//    }


}
