package com.example.timeweb;

import com.example.timeweb.domain.Time;
import com.example.timeweb.service.TimeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TimeWebApplicationTests {
    @Autowired
    private TimeService timeService;

    /**
     * current business hours
     */
    @Test
    void todayInfoTest() {
        Time todayInfo = timeService.getTodayInfo();
        System.out.println("todayInfo = " + todayInfo);
    }

    @Test
    void hoursResetTest() {
        Time defaultTime = timeService.resetDefaultTime();
        System.out.println("defaultTime = " + defaultTime);
    }

    @Test
    void hoursSetTest() {
        Time mo = timeService.hoursSet(new Time("Mo", "12:00", "00:00"));
        System.out.println("mo = " + mo);
    }

    @Test
    void hoursClosedTest() {
        Time time = new Time();
        time.setDayInfo("Tu").setCloseShop("closed");
        Time serviceTime = timeService.closeDay(time);
        System.out.println("time = " + serviceTime);
    }

}
