package com.yt.retrievalartifact.core.search;

import com.yt.retrievalartifact.core.model.Condition;
import com.yt.retrievalartifact.core.model.Things;

import java.util.List;

/**
 * 文件检索业务
 */
public interface ThingSearch {
    /**
     * 根据condition条件检索数据9
     * @param condition
     * @return
     */
    List<Things> search(Condition condition);
}
