package com.belonk.test.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Data
@EqualsAndHashCode
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
    private long iTotalDisplayRecords;
    /**
     * dataTables匹配请求的跟踪标志（页面传过来的，原样返回）
     * Tracking flag for DataTables to match requests
     */
    // @JsonProperty("sEcho")
    private int sEcho;
    /**
     * 数据
     * The data to display on this page
     */
    private List<T> aaData = Collections.emptyList();
}
