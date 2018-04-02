package com.spring.service;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.spring.dao.ResultDAO;

@Service("scheduler")
public class Scheduler implements Runnable {

	@SuppressWarnings("rawtypes")
	ScheduledFuture scheduledFuture;

	TaskScheduler taskScheduler;
	
	@Autowired
	ResultDAO resultDAO;

	// this method will kill previous scheduler if exists and will create a new
	// scheduler with given cron expression
	public void reSchedule(String cronExpression) {
		if (taskScheduler == null) {
			this.taskScheduler = new ConcurrentTaskScheduler();
		}
		if (this.scheduledFuture != null) {
			this.scheduledFuture.cancel(true);
		}
		this.scheduledFuture = this.taskScheduler.schedule(this, new CronTrigger(cronExpression));
	}

	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		System.out.println(new Date() + " Cron Job Thread Name:" + threadName);
	}

//	@PostConstruct
//	public void initializeScheduler() {
//		doReschedule();
//	}
	
	public void doReschedule(){
		String cronExpression = resultDAO.getCronExpression();
		System.out.println("cronExpression: " + cronExpression);
		this.reSchedule(cronExpression);
	}

}
