package com.cnsmash.cspr.api.service;

import com.cnsmash.cspr.api.dto.CalendarDayDto;

import java.util.List;

public interface ICalendarService {

    /**
     * 根据年份/月份获取对应的活动日历
     * @param year 年份
     * @param month 月份
     * @return 对应月份日历数组
     */
    public List<CalendarDayDto> getCalendarMonth(int year, int month);
}
