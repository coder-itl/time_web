### README

+ `dependencies`

  ```xml
  <dependencies>
      <!-- springmvc web -->
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-web</artifactId>
      </dependency>
      <!-- devtools 热加载 -->
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-devtools</artifactId>
          <scope>runtime</scope>
          <optional>true</optional>
      </dependency>
      <!--  Simplified entity class -->
      <dependency>
          <groupId>org.projectlombok</groupId>
          <artifactId>lombok</artifactId>
          <optional>true</optional>
      </dependency>
      <!--  test -->
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-test</artifactId>
          <scope>test</scope>
      </dependency>
      <!--  dimain to json -->
      <dependency>
          <groupId>com.alibaba</groupId>
          <artifactId>fastjson</artifactId>
          <version>1.2.78</version>
      </dependency>
  </dependencies>
  ```

+ `domain`

  ```java
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
  	// status
      private String closeShop;
      // start time
      private String startTime;
      // end time
      private String endTime;
      // Mo?Tu.....
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
  
  ```

+ `Service`

  ```java
  package com.example.timeweb.service.impl;
  
  import com.example.timeweb.domain.Time;
  import com.example.timeweb.service.TimeService;
  import com.example.timeweb.utils.TimeFormatUtils;
  import org.springframework.stereotype.Service;
  
  import java.util.Calendar;
  // specification
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
  
  ```

+ `Controller`

  ```java
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
       * current business hours
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
       * Resetting the opening hours to the default hours
       *
       * @return
       */
      @PutMapping("/hours/reset")
      public String hoursReset() {
          Time defaultTime = timeService.resetDefaultTime();
          return JSON.toJSONString(defaultTime);
      }
  
      /**
       * Setting new hours for a day
       *
       * @return
       */
      @PutMapping("/hours/set")
      public String hoursSet(Time time) {
          Time setTime = timeService.hoursSet(time);
          return JSON.toJSONString(setTime);
      }
  
      /**
       * Closing a day
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
  
  ```

+ `Utils`

  ```java
  package com.example.timeweb.utils;
  
  import java.util.Calendar;
  
  /**
   * Get week information
   */
  public class TimeFormatUtils {
      public static String getWeekDay(Calendar c) {
          if (c == null) {
              return "Mo";
          }
  
          if (Calendar.MONDAY == c.get(Calendar.DAY_OF_WEEK)) {
              return "Mo";
          }
          if (Calendar.TUESDAY == c.get(Calendar.DAY_OF_WEEK)) {
              return "Tu";
          }
          if (Calendar.WEDNESDAY == c.get(Calendar.DAY_OF_WEEK)) {
              return "We";
          }
          if (Calendar.THURSDAY == c.get(Calendar.DAY_OF_WEEK)) {
              return "Th";
          }
          if (Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK)) {
              return "Fr";
          }
          if (Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)) {
              return "Sa";
          }
          if (Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK)) {
              return "So";
          }
  
          return "Mo";
      }
  }
  
  ```

+ `Test`

  + `current business hours`

    ```java
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
    
    }
    
    ```

    |                ` current business hours Test`                |      |
    | :----------------------------------------------------------: | ---- |
    | ![image-20220902093919626](C:\Users\coderitl\AppData\Roaming\Typora\typora-user-images\image-20220902093919626.png) |      |

  + `hoursResetTest`

    ```java
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
    
        @Test
        void hoursResetTest() {
            Time defaultTime = timeService.resetDefaultTime();
            System.out.println("defaultTime = " + defaultTime);
        }
    
    }
    
    ```

    |                       `hoursResetTest`                       |
    | :----------------------------------------------------------: |
    | ![image-20220902094113792](C:\Users\coderitl\AppData\Roaming\Typora\typora-user-images\image-20220902094113792.png) |

  + `hoursSet`

    ```java
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
    
        @Test
        void hoursSetTest() {
            Time mo = timeService.hoursSet(new Time("Mo", "12:00", "00:00"));
            System.out.println("mo = " + mo);
        }
    
    }
    
    ```

    |                        `hoursSetTest`                        |
    | :----------------------------------------------------------: |
    | ![image-20220902094317032](C:\Users\coderitl\AppData\Roaming\Typora\typora-user-images\image-20220902094317032.png) |

  + `closedTest`

    ```java
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
    
        @Test
        void hoursClosedTest() {
            Time time = new Time();
            time.setDayInfo("Tu").setCloseShop("closed");
            Time serviceTime = timeService.closeDay(time);
            System.out.println("time = " + serviceTime);
        }
    
    }
    
    ```

    |                         `closeTest`                          |
    | :----------------------------------------------------------: |
    | ![image-20220902094435392](C:\Users\coderitl\AppData\Roaming\Typora\typora-user-images\image-20220902094435392.png) |

+ `Controller`

  ```java
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
       * current business hours
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
       * Resetting the opening hours to the default hours
       *
       * @return
       */
      @PutMapping("/hours/reset")
      public String hoursReset() {
          Time defaultTime = timeService.resetDefaultTime();
          return JSON.toJSONString(defaultTime);
      }
  
      /**
       * Setting new hours for a day
       *
       * @return
       */
      @PutMapping("/hours/set")
      public String hoursSet(Time time) {
          Time setTime = timeService.hoursSet(time);
          return JSON.toJSONString(setTime);
      }
  
      /**
       * Closing a day
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
  
  ```

  + `current business hours`

    |                   `current business hours`                   |
    | :----------------------------------------------------------: |
    | ![image-20220902094713592](C:\Users\coderitl\AppData\Roaming\Typora\typora-user-images\image-20220902094713592.png) |

  + `closed API`

    |                         `closed API`                         |      |
    | :----------------------------------------------------------: | ---- |
    | ![image-20220902094645373](C:\Users\coderitl\AppData\Roaming\Typora\typora-user-images\image-20220902094645373.png) |      |

    

+ `CI/CD`

  ```yml
  # This workflow will build a Java project with Maven, and cache/restore any dependencies to improve the workflow execution time
  # For more information see: https://help.github.com/actions/language-and-framework-guides/building-and-testing-java-with-maven
  
  name: Java CI with Maven
  
  on:
    push:
      branches: [ "main" ]
    pull_request:
      branches: [ "main" ]
  
  jobs:
    build:
  
      runs-on: ubuntu-latest
  
      steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
          cache: maven
      - name: Build with Maven
        run: mvn -B package --file pom.xml
  ```

  |                        `ci/cd run ok`                        |
  | :----------------------------------------------------------: |
  | ![image-20220902100122278](C:\Users\coderitl\AppData\Roaming\Typora\typora-user-images\image-20220902100122278.png) |
  
  
