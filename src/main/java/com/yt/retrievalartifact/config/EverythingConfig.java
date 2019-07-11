package com.yt.retrievalartifact.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.SQLOutput;

/**
 * 配置
 */
@ToString
public class EverythingConfig {
    /**
     * 保证可见性
     */
    private  static volatile EverythingConfig config;

    /**
     * 索引目录
     */
    @Getter
    private  HandlerPath handlerPath=HandlerPath.getDefaultHandlPath();

    /**
     * 返回的最大文件数量
     */
    @Getter
    @Setter
    private Integer maxReturn=30;

    /**
     * 是否开启构建索引
     * 默认：程序运行时索引关闭
     *
     * 开启索引：
     *  1）运行程序时，指定参数
     *  2）通过功能的命令index
     */
    @Getter
    @Setter
    private  Boolean enableBuildIndex=false;

    /**
     * 默认检索时depth的排序规则
     *  true：降序
     *  false：升序
     *  默认是降序
     */
    @Getter
    @Setter
    private Boolean orderByDepthDesc=false;

    private EverythingConfig(){

    }
    public  static EverythingConfig getInstance(){
        if(config ==null){
            synchronized (EverythingConfig.class){
                if(config==null){
                    config=new EverythingConfig();
                }
            }
        }
        return config;
    }


}
