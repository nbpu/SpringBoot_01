package com.example.demo.batchBussiness;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * spring注解的形式实现批量
 * 处理批量业务
 */
@Component
public class BatchView {
    private static Logger logger = LoggerFactory.getLogger(BatchView.class);
    @Scheduled(cron = "0 */30 * * * ?")
    public void testTask() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("每30m执行一次:时间{}", format.format(new Date()));
    }

//每隔5秒执行一次：            @Scheduled(cron = "*/5 * * * * ?")
//每隔1分钟执行一次：           @Scheduled(cron = "0 */1 * * * ?")
//每天23点执行一次：           @Scheduled(cron = "0 0 23 * * ?")
//每天凌晨1点执行一次：          @Scheduled(cron = "0 0 1 * * ?")
//每月1号凌晨1点执行一次：      @Scheduled(cron = "0 0 1 1 * ?")
//每月最后一天23点执行一次：     @Scheduled(cron = "0 0 23 L * ?")
//每周星期天凌晨1点实行一次：     @Scheduled(cron = "0 0 1 ? * L")
//在26分、29分、33分执行一次：     @Scheduled(cron = " 0 26,29,33 * * * ?")
//每天的0点、13点、18点、21点都执行一次：  @Scheduled(cron = " 0 0 0,13,18,21 * * ?")
//0 0 10,14,16 * * ? 每天上午10点，下午2点，4点
//0 0/30 9-17 * * ? 早晨9点到下午5点，每半小时执行一次
//0 0 12 ? * WED 表示每个星期三中午12点


}
