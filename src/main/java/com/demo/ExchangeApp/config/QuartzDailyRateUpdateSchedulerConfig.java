package com.demo.ExchangeApp.config;

import com.demo.ExchangeApp.job.DailyCurrencyRateUpdateJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class QuartzDailyRateUpdateSchedulerConfig {

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        return new SpringBeanJobFactory();
    }

    @Bean
    public JobDetail dailyRateUpdateJobDetail() {
        return JobBuilder.newJob(DailyCurrencyRateUpdateJob.class)
                .withIdentity("dailyCurrencyRateUpdateJob")
                .withDescription("Update currency rates in the database")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger dailyRateUpdateJobTrigger(JobDetail dailyRateUpdateJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(dailyRateUpdateJobDetail)
                .withIdentity("dailyCurrencyRateUpdateJobTrigger")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(6, 0))
                .build();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactory(Trigger dailyRateUpdateJobTrigger, JobDetail dailyRateUpdateJobDetail) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setJobDetails(dailyRateUpdateJobDetail);
        schedulerFactory.setTriggers(dailyRateUpdateJobTrigger);
        return schedulerFactory;
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactory) throws SchedulerException {
        return schedulerFactory.getScheduler();
    }

}
