package com.yt.retrievalartifact.core.model;

import lombok.Data;

/**
 * 检索条件的模型类
 */

@Data
public class Condition {

    /**
     * 文件名
     */
    private  String name;
    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 限制的数量
     */
    private Integer limit;

    /**
     * 是否按照路径深度进行升序排列
     */
    public  Boolean orderByDepthAsc;

}
