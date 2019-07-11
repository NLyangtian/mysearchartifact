package com.yt.retrievalartifact.core.intercepter;

import java.io.File;

/**
 * 文件拦截器处理指定文件
 */
public interface FileInterceptor {
    void apply(File file);
}
