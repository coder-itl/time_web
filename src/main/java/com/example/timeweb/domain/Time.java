package com.example.timeweb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Accessors(chain = true): Enable chaining
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Time {
    private String Tu;

    private String closeShop;
    private String startTime;
    private String endTime;
    private String dayInfo;

    public Time(String tu) {
        Tu = tu;
    }

    public Time(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Time(String dayInfo, String startTime, String endTime) {
        this.dayInfo = dayInfo;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
