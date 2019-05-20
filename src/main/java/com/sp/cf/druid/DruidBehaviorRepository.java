package com.sp.cf.druid;

/**
 * Created by hubeilong
 * 2018/2/8.
 */
public interface DruidBehaviorRepository {

    void getBehaviorListByQuering() throws Exception;

    String getBehaviorListBySql() throws Exception;
}
