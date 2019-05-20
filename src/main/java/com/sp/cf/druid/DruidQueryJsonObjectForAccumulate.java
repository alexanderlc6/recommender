package com.sp.cf.druid;

import java.util.List;

/**
 * alexlu
 * 2018/2/8.
 */
public class DruidQueryJsonObjectForAccumulate {
    /**
     * queryType : timeseries dataSource : IoTCloud_Meter granularity : all descending : true filter :
     * {"type":"and","fields":[{"type":"selector","dimension":"deviceId","value":"20170731-10"},{"type":"selector","dimension":"metric","value":"enn.meter.gas.measure.c"}]}
     * aggregations : [{"type":"doubleLast","name":"value_max","fieldName":"value_max"}]
     * postAggregations : [] intervals : ["2017-07-31/2017-07-31T03:32:37.786"]
     */

    private String queryType;
    private String dataSource;
    private String granularity;
    private String descending;
    private FilterBean filter;
    private List<AggregationsBean> aggregations;
    private List<?> postAggregations;
    private List<String> intervals;

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }

    public String getDescending() {
        return descending;
    }

    public void setDescending(String descending) {
        this.descending = descending;
    }

    public FilterBean getFilter() {
        return filter;
    }

    public void setFilter(FilterBean filter) {
        this.filter = filter;
    }

    public List<AggregationsBean> getAggregations() {
        return aggregations;
    }

    public void setAggregations(List<AggregationsBean> aggregations) {
        this.aggregations = aggregations;
    }

    public List<?> getPostAggregations() {
        return postAggregations;
    }

    public void setPostAggregations(List<?> postAggregations) {
        this.postAggregations = postAggregations;
    }

    public List<String> getIntervals() {
        return intervals;
    }

    public void setIntervals(List<String> intervals) {
        this.intervals = intervals;
    }

    public static class FilterBean {
        /**
         * type : and fields :
         * [{"type":"selector","dimension":"deviceId","value":"20170731-10"},{"type":"selector","dimension":"metric","value":"enn.meter.gas.measure.c"}]
         */

        private String type;
        private List<FieldsBean> fields;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<FieldsBean> getFields() {
            return fields;
        }

        public void setFields(List<FieldsBean> fields) {
            this.fields = fields;
        }

        public static class FieldsBean {
            /**
             * type : selector dimension : deviceId value : 20170731-10
             */

            private String type;
            private String dimension;
            private String value;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDimension() {
                return dimension;
            }

            public void setDimension(String dimension) {
                this.dimension = dimension;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }

    public static class AggregationsBean {
        /**
         * type : doubleLast name : value_max fieldName : value_max
         */

        private String type;
        private String name;
        private String fieldName;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }
    }
}
