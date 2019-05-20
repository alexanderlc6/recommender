package com.sp.cf.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Druid数据库相关配置 alexlu
 */
@Component
@ConfigurationProperties(prefix = "druid.query")
public class DruidConfig {

    /**
     * @Fields: JDBC方式查询的地址
     * @since: JDK1.8
     */
    private String jdbcurl;
    /**
     * @Fields: Querying方式查询的地址
     * @since: JDK1.8
     */
    private String queryingurl;
    /**
     * @Fields: 数据源名称
     * @since: JDK1.8
     */
    private String userbehaviordatasource;

    public String getJdbcurl() {
        return jdbcurl;
    }

    public void setJdbcurl(String jdbcurl) {
        this.jdbcurl = jdbcurl;
    }

    public String getQueryingurl() {
        return queryingurl;
    }

    public void setQueryingurl(String queryingurl) {
        this.queryingurl = queryingurl;
    }

    public String getUserbehaviordatasource() {
        return userbehaviordatasource;
    }

    public void setUserbehaviordatasource(String userbehaviordatasource) {
        this.userbehaviordatasource = userbehaviordatasource;
    }
}
