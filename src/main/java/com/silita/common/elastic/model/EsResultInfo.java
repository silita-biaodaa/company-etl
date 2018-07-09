package com.silita.common.elastic.model;

import java.util.List;

public class EsResultInfo {

    private long total=-1;

    private List<? extends ElasticEntity> dataList=null;

    public EsResultInfo(long total, List<ElasticEntity> dataList){
        this.total = total;
        this.dataList = dataList;
    }

    public long getTotal() {
        return total;
    }

    public List<? extends ElasticEntity> getDataList() {
        return dataList;
    }
}
