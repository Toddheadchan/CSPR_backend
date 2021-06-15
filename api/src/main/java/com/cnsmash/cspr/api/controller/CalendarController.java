package com.cnsmash.cspr.api.controller;

import com.cnsmash.cspr.api.dto.CalendarDayDto;
import com.cnsmash.cspr.api.service.ICalendarService;
import com.cnsmash.cspr.framework.api.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/cspr/calendar")
public class CalendarController {

    @Autowired
    ICalendarService iCalendarService;

    @GetMapping("/month/{year}/{month}")
    @ResponseBody
    public ApiResult<List<CalendarDayDto> > getCalendarMonth(@PathVariable(value = "year") int year, @PathVariable("month") int month) {
        List<CalendarDayDto> calendarList = iCalendarService.getCalendarMonth(year, month);
        ApiResult<List<CalendarDayDto> > result = new ApiResult<>();
        return result.ok(calendarList);
    }
}
