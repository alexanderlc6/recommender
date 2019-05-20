package com.sp.cf.druid.impl;

import com.sp.cf.common.DruidGranularityEnum;
import com.sp.cf.common.DruidQueryAggregationsEnum;
import com.sp.cf.common.DruidQueryLogicalExpressionEnum;
import com.sp.cf.common.DruidQueryTypeEnum;
import com.sp.cf.config.DruidConfig;
import com.sp.cf.constant.DruidConstant;
import com.sp.cf.druid.DruidBehaviorRepository;
import com.sp.cf.druid.DruidQueryJsonObjectForAccumulate;
import com.sp.cf.util.DruidQueryUtil;
import com.sp.cf.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * alexlu
 */
@Component
@Configuration
public class DruidBehaviorRepositoryImpl implements DruidBehaviorRepository {

    @Autowired
    private DruidQueryUtil druidQueryUtil;
    @Autowired
    private DruidConfig druidConfig;

    @Override
    public void getBehaviorListByQuering() throws Exception {
        // 组装查询指令
        DruidQueryJsonObjectForAccumulate druidQueryJsonObjectForAccumulate =
                new DruidQueryJsonObjectForAccumulate();
        druidQueryJsonObjectForAccumulate.setQueryType(DruidConstant.QUERY_TYPE_TIMESERIES);
        druidQueryJsonObjectForAccumulate.setDataSource(druidConfig.getUserbehaviordatasource());
        druidQueryJsonObjectForAccumulate.setDescending(Boolean.TRUE.toString());
        druidQueryJsonObjectForAccumulate
                .setGranularity(DruidGranularityEnum.ALL.name().toLowerCase());

        DruidQueryJsonObjectForAccumulate.FilterBean filterBean =
                new DruidQueryJsonObjectForAccumulate.FilterBean();
        filterBean.setType(DruidQueryLogicalExpressionEnum.AND.name().toLowerCase());
        List<DruidQueryJsonObjectForAccumulate.FilterBean.FieldsBean> fields = new ArrayList<>();
//        DruidQueryJsonObjectForAccumulate.FilterBean.FieldsBean fieldsBean =
//                new DruidQueryJsonObjectForAccumulate.FilterBean.FieldsBean();
//        fieldsBean.setType(DruidQueryFiltersEnum.SELECTOR.name().toLowerCase());
//        fieldsBean.setDimension(DruidConstant.DIMENSION_DEVICE_ID);
//        fieldsBean.setValue(deviceId);
//        fields.add(fieldsBean);
//
//        fieldsBean = new DruidQueryJsonObjectForAccumulate.FilterBean.FieldsBean();
//        fieldsBean.setType(DruidQueryFiltersEnum.SELECTOR.name().toLowerCase());
//        fieldsBean.setDimension(DruidConstant.DIMENSION_METRIC);
//        fieldsBean.setValue(metric);
//        fields.add(fieldsBean);

        filterBean.setFields(fields);
        druidQueryJsonObjectForAccumulate.setFilter(filterBean);

        List<DruidQueryJsonObjectForAccumulate.AggregationsBean> aggregations = new ArrayList<>();
        DruidQueryJsonObjectForAccumulate.AggregationsBean aggregationsBean =
                new DruidQueryJsonObjectForAccumulate.AggregationsBean();
        aggregationsBean.setType(DruidQueryAggregationsEnum.doubleLast.name());
        aggregationsBean.setName(DruidConstant.NAME_VALUE_MAX);
        aggregationsBean.setFieldName(DruidConstant.NAME_VALUE_MAX);
        aggregations.add(aggregationsBean);
        druidQueryJsonObjectForAccumulate.setAggregations(aggregations);

        druidQueryJsonObjectForAccumulate.setPostAggregations(new ArrayList<>());

        String tmpBeginTime = "2018-01-06T00:00:00.000";
        String tmpEndTime = "2018-01-10T23:59:59.999";
        List<String> intervalListForBegin = new ArrayList<>();
        intervalListForBegin.add(tmpBeginTime.concat("/").concat(tmpEndTime));
        druidQueryJsonObjectForAccumulate.setIntervals(intervalListForBegin);
        String queryCmdForBegin = JsonUtils.writeValueAsString(druidQueryJsonObjectForAccumulate);
        String queryResultForBegin =
                druidQueryUtil.queryData(queryCmdForBegin, DruidQueryTypeEnum.QUERYING);

        System.out.println( "druid-result: " + queryResultForBegin);
    }

    @Override
    public String getBehaviorListBySql() throws Exception {
        String queryCmd = "{\"query\":\"SELECT userId as a_userId,eventId as b_eventId FROM " + druidConfig.getUserbehaviordatasource() + " WHERE 1=1 ";

        /*if (dateTimes.size() > 0) {
            queryCmd += "AND __time  <= '" + dateTimes.get(0) + "' AND __time >= '" + dateTimes.get(dateTimes.size() - 1) + "' ";
        }

        queryCmd += "AND deviceId = '" + deviceId + "' ";
        queryCmd += " ORDER BY __time DESC ";*/
        queryCmd += "\"}";

        return druidQueryUtil.queryData(queryCmd, DruidQueryTypeEnum.JDBC);


    }
}
