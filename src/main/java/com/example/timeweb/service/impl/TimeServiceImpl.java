package com.example.timeweb.service.impl;

import com.example.timeweb.domain.Time;
import com.example.timeweb.service.TimeService;
import com.example.timeweb.utils.TimeFormatUtils;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class TimeServiceImpl implements TimeService {
    private String dayInfo = TimeFormatUtils.getWeekDay(Calendar.getInstance()).toString();

    @Override
    public Time getTodayInfo() {
        if (dayInfo.equals("Mo")) {
            return new Time(dayInfo, "9:00", "17:00");
        }
        if (dayInfo.equals("Tu")) {
            return new Time(dayInfo, "closed");
        }
        if (dayInfo.equals("We")) {
            return new Time(dayInfo, "9:00", "17:00");
        }
        if (dayInfo.equals("Th")) {
            return new Time(dayInfo, "9:00", "17:00");
        }
        if (dayInfo.equals("Fr")) {
            return new Time(dayInfo, "8:00", "19:00");
        }
        if (dayInfo.equals("Sa")) {
            return new Time(dayInfo, "8:00", "19:00");
        }
        if (dayInfo.equals("So")) {
            return new Time(dayInfo, "9:00", "19:00");
        }
        return null;
    }

    @Override
    public Time resetDefaultTime() {
        return new Time("Mo", "9:00", "17:00");
    }

    @Override
    public Time hoursSet(Time time) {
        return new Time(time.getDayInfo(), time.getStartTime(), time.getEndTime());
    }

    @Override
    public Time closeDay(Time time) {
        return new Time(time.getDayInfo(), time.getCloseShop());
    }
}
