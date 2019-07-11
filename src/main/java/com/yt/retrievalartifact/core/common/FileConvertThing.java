package com.yt.retrievalartifact.core.common;

import com.yt.retrievalartifact.core.model.FileType;
import com.yt.retrievalartifact.core.model.Things;

import java.io.File;

/**
 * 文件对象转换Things对象的辅助类
 */
public class FileConvertThing {
    public static Things convert(File file){
        Things things=new Things();
        things.setName(file.getName());
        things.setPath(file.getAbsolutePath());
        String name=file.getName();
        /**
         * 目录->*
         * 文件->有扩展名，通过扩展名获取FileType
         *      无扩展，*
         *
         */

        int index=name.lastIndexOf(".");

        String extend="*";
        if(index!=-1 && index+1 < name.length()){

           extend= name.substring(index+1);
        }
        things.setFileType(FileType.lookUpByExtend(extend));
        things.setDepth(0);
        return things;
    }

    private  static  int computePathDepth(String path){
        //计算文件的路径的深度
        int depth=0;
        for(char c:path.toCharArray()){
            if(c==File.separatorChar){
                depth+=1;
            }
        }
        return depth;
    }

    public static void main(String[] args) {
        File file=new File("D:\\java_prc\\newthread\\src\\www\\ThreadSynchronizded\\TestLock.java");
        Things things=FileConvertThing.convert(file);
        System.out.println(things);
    }
}
