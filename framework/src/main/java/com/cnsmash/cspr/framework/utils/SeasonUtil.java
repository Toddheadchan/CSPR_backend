package com.cnsmash.cspr.framework.utils;

import java.time.LocalDateTime;

public class SeasonUtil {

    /**
     * 根据日期获取赛季编号
     * @param date 日期
     * @return 日期对应的赛季编号
     */
    public static int getSeasonByDate(LocalDateTime date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        if (year < 2021) {
            return 0;
        }
        return (year - 2021) * 2 + (month <=6 ? 0 : 1);
    }

    /**
     * 获取当前时间所在的赛季编号
     * @return 当前时间的赛季编号
     */
    public static int getCurrentSeason() {
        return getSeasonByDate(LocalDateTime.now());
    }


    /**
     * 根据赛季编号获取赛季起始时间
     * @param season 赛季编号
     * @return 赛季开始时间
     */
    public static LocalDateTime getSeasonStartDate(int season) {
        LocalDateTime ret;
        if (season == 0) {
            ret = LocalDateTime.of(2020, 7, 1, 0, 0);
        } else {
            ret = LocalDateTime.of((season - 1) / 2 + 2021, 7 - 6 * (season % 2), 1, 0, 0);
        }
        return ret;
    }

    public static LocalDateTime getSeasonEndDate(int season) {
        LocalDateTime start = getSeasonStartDate(season);
        return start.minusDays(1);
    }

}
