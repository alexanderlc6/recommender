package com.sp.cf.util;

import com.sp.cf.common.DruidQueryTypeEnum;
import com.sp.cf.config.DruidConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Druid数据库查询Util
 * alexlu
 * 2018/2/7.
 */
@Configuration
public class DruidQueryUtil {

    private static Logger logger = LoggerFactory.getLogger(DruidQueryUtil.class);

    @Autowired
    private DruidConfig druidConfig;

    /**
     * 查询数据
     *
     * @param queryCmd  查询命令
     * @param queryType 查询类型
     * @return 查询结果：JSON字符串
     */
    public String queryData(String queryCmd, DruidQueryTypeEnum queryType) throws Exception {
        HttpUtils httpUtils = HttpUtils.getInstance();
        //String url = queryType.name().equals(DruidQueryTypeEnum.JDBC.name()) ? druidConfig.getJdbcurl() : druidConfig.getQueryingurl();
        String url = "http://10.19.248.200:31035/druid/v2/sql";
        long begin = System.currentTimeMillis();
        String result = httpUtils.postJson(url, queryCmd);
        if(logger.isDebugEnabled()){
            logger.debug("druid查询-耗时:{}毫秒,url:{},query:{}",System.currentTimeMillis()-begin,url,queryCmd);
        }
        return result;
    }

    public static void main(String[] args) {
        /** jdbc查询方式 **/
        String queryCmd = "{\"query\":\"select userId as a_userId,eventId as b_eventId from " + "User_Behavior where 1=1" + "\"}";

        try {
            String strJson = new DruidQueryUtil().queryData(queryCmd, DruidQueryTypeEnum.JDBC);
            System.out.println(strJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
