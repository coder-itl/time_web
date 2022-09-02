package com.example.timeweb.service;

import com.example.timeweb.domain.Time;

public interface TimeService {
    /**
     * get today info
     *
     * @return
     */
    Time getTodayInfo();

    /**
     * reset default Time
     *
     * @return
     */
    Time resetDefaultTime();

    Time hoursSet(Time time);

    Time closeDay(Time time);
}
