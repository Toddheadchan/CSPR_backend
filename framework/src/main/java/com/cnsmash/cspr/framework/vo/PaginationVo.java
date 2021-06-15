package com.cnsmash.cspr.framework.vo;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

public class PaginationVo<E> implements Serializable {
    private static final long serialVersionUID = -2487351575274660620L;

    private int total; // Total records number without conditions
    private int display; // Total records number with conditions
    private List<E> data;

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public PaginationVo() {
        super();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int iTotalRecords) {
        this.total = iTotalRecords;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int iTotalDisplayRecords) {
        this.display = iTotalDisplayRecords;
    }

    /*
     * @describe TODO
     * @param [sEcho, iTotalRecords, iTotalDisplayRecords, data]
     * @return
     */
    public PaginationVo(Integer iTotalRecords,
                        Integer iTotalDisplayRecords, List<E> data) {
        this.total = iTotalRecords;
        this.display = iTotalDisplayRecords;
        this.data = data;
    }

    public static <E> PaginationVo<E> getPagination(PageInfo<E> pageInfo) {
        return new PaginationVo<>((int) pageInfo.getTotal(), (int) pageInfo.getList().size(), pageInfo.getList());
    }
}
