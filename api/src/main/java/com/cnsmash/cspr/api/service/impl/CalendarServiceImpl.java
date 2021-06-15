package com.cnsmash.cspr.api.service.impl;

import com.cnsmash.cspr.api.dto.CalendarDayDto;
import com.cnsmash.cspr.api.dto.TournamentLiteDto;
import com.cnsmash.cspr.api.mapper.TournamentMapper;
import com.cnsmash.cspr.api.service.ICalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedList;
import java.util.List;

@Service
public class CalendarServiceImpl implements ICalendarService {

    @Autowired
    TournamentMapper tournamentMapper;

    @Override
    public List<CalendarDayDto> getCalendarMonth(int year, int month) {
        // 记录对应月份的首日与最后一日，求获取比赛列表的起始时间与结束时间
        LocalDateTime firstDayOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime lastDayOfMonth = firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());
        int weekDayOfFirstDay = firstDayOfMonth.getDayOfWeek().getValue();
        int weekDayOfLastDay = lastDayOfMonth.getDayOfWeek().getValue();
        LocalDateTime startTime = firstDayOfMonth.minusDays(weekDayOfFirstDay);
        LocalDateTime endTime = lastDayOfMonth.plusDays(6 - weekDayOfLastDay).plusDays(1);
        // 根据时间范围获取日历中比赛列表
        List<TournamentLiteDto> tournaments = tournamentMapper.getTournamentLiteInTimeRange(startTime, endTime);

        // 返回值的列表
        List<CalendarDayDto> result = new LinkedList<>();
        // 按日遍历时间生成需要返回的列表
        int TournamentIterPointer = 0;
        LocalDateTime dateIter = startTime;
        while (dateIter.compareTo(endTime) < 0) {
            CalendarDayDto calendarDay = new CalendarDayDto(dateIter);
            LocalDateTime tomorrow = dateIter.plusDays(1);
            while (TournamentIterPointer < tournaments.size()
                    && tournaments.get(TournamentIterPointer).getDate().compareTo(tomorrow) == -1) {
                calendarDay.addTournament(tournaments.get(TournamentIterPointer));
                TournamentIterPointer += 1;
            }
            result.add(calendarDay);
            dateIter = tomorrow;
        }

        return result;
    }
}
