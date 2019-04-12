package com.belonk.test.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

/**
 * 基于<a href="https://datatables.net/">DataTable</a>的分页结果对象。
 *
 * @author sun
 * @version 1.0
 * @since 1.0
 */
// @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "pager")
// @JsonSubTypes({@JsonSubTypes.Type(value = MyPager.class, name = "myPager")})
public class DataTablePager<T> {
    /**
     * 过滤前总记录数
     * Number of records in the data set, not accounting for filtering
     */
    private long iTotalRecords;
    /**
     * 过滤后总记录数
     * Number of records in the data set, accounting for filtering
     */
    @JsonProperty("iTotalDisplayRecords")
    private long iTotalDisplayRecords;
    /**
     * dataTables匹配请求的跟踪标志（页面传过来的，原样返回）
     * Tracking flag for DataTables to match requests
     */
    @JsonProperty("sEcho")
    private int sEcho;
    /**
     * 数据
     * The data to display on this page
     */
    private List<T> aaData = Collections.emptyList();

    @JsonProperty("iTotalRecords")
    public long getITotalRecords() {
        return iTotalRecords;
    }

    @JsonProperty("iTotalRecords")
    public void setITotalRecords(long iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    @JsonProperty("iTotalDisplayRecords")
    public long getITotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    @JsonProperty("iTotalDisplayRecords")
    public void setITotalDisplayRecords(long iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    @JsonProperty("sEcho")
    public int getSEcho() {
        return sEcho;
    }

    @JsonProperty("sEcho")
    public void setSEcho(int sEcho) {
        this.sEcho = sEcho;
    }

    public List<T> getAaData() {
        return aaData;
    }

    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }
}
