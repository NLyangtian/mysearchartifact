package com.yt.retrievalartifact.core.intercepter;

import com.yt.retrievalartifact.core.model.Things;

/**
 * 检索结果things的拦截器
 */
public interface ThingInterceptor {

    void apply(Things things);
}
