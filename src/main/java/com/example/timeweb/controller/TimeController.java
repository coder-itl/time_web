package com.example.timeweb.controller;

import com.alibaba.fastjson.JSON;
import com.example.timeweb.domain.Time;
import com.example.timeweb.service.TimeService;
import com.example.timeweb.utils.TimeFormatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;


/**
 * 星期一 Monday 缩写 Mon.               9:00 ~ 17:00
 * 星期二 Tuesday 缩写 Tues.             closed
 * 星期三 Wednesday 缩写 Wed.            9:00 ~ 17:00
 * 星期四 Thursday 缩写 Thur. / Thurs.   9:00 ~ 17:00
 * 星期五 Friday 缩写 Fri.               8:00 ~ 19:00
 * 星期六 Saturday 缩写 Sat.             8:00 ~ 19:00
 * 星期日 Sunday 缩写 Sun.               8:00 ~ 19:00
 *
 * @Slf4j: output log
 * @CrossOrigin: Cross-domain support
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")
public class TimeController {
    @Autowired
    private TimeService timeService;


    /**
     * get work time
     *
     * @return
     */
    @GetMapping("/hours")
    public String hours() {
        log.info("Now Time: [{}]", TimeFormatUtils.getWeekDay(Calendar.getInstance()));
        Time todayInfo = timeService.getTodayInfo();
        // 实体类转换为 json
        return JSON.toJSONString(todayInfo);
    }

    /**
     * set default hours
     *
     * @return
     */
    @PutMapping("/hours/reset")
    public String hoursReset() {
        Time defaultTime = timeService.resetDefaultTime();
        return JSON.toJSONString(defaultTime);
    }

    /**
     * Setting new hours
     *
     * @return
     */
    @PutMapping("/hours/set")
    public String hoursSet(Time time) {
        Time setTime = timeService.hoursSet(time);
        return JSON.toJSONString(setTime);
    }

    /**
     * today closing
     *
     * @return
     */
    @GetMapping("/hours/closed")
    public String hoursClose() {
        Time time = new Time();
        time.setDayInfo("Tu").setCloseShop("closed");
        Time closeInfo = timeService.closeDay(time);
        return JSON.toJSONString(closeInfo);
    }
}
