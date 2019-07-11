package com.yt.retrievalartifact.core.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Things {
    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件路径深度
     */
    private Integer depth;

    /**
     * 文件类型
     */
    private FileType fileType;
}
