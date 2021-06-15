package com.cnsmash.cspr.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@ApiModel("日历单日结构")
@Data
public class CalendarDayDto {

    @ApiModelProperty("年份")
    private int year;

    @ApiModelProperty("月份")
    private int month;

    @ApiModelProperty("日期")
    private int day;

    @ApiModelProperty("完整日期， 格式（yyyy-mm-dd）")
    private String date;

    @ApiModelProperty("是否公共假期")
    private Boolean holiday;

    @ApiModelProperty("比赛列表")
    private List<TournamentLiteDto> tournament;

    // 无参数构造函数
    public CalendarDayDto() {}

    // 简易构造函数，比赛数据后续添加
    public CalendarDayDto(LocalDateTime dateTime) {
        this.year = dateTime.getYear();
        this.month = dateTime.getMonthValue();
        this.day = dateTime.getDayOfMonth();
        this.date = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.holiday = false;
        this.tournament = new LinkedList<>();
    }

    // 日期添加比赛
    public void addTournament(TournamentLiteDto tournament) {
        this.tournament.add(tournament);
    }
}
