package com.yt.retrievalartifact.core.dao;

import com.yt.retrievalartifact.core.model.Condition;
import com.yt.retrievalartifact.core.model.Things;

import java.util.List;

/**
 * 数据库访问的对象
 */

public interface FileIndexDao {
    //File-->Things对象--->Database table Record

    /**
     * 插入
     * @param things
     */
    void insert(Things things);

    /**
     * 删除
     * @param things
     */
    void delete(Things things);

    /**
     * 查询
     * @param condition
     * @return
     */
    List<Things> query(Condition condition);
}
