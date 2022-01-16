package com.codechallenge.scheduler;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import com.codechallenge.monitorable.MonitorableServer;
import com.codechallenge.service.ServerMonitoringService;

public class MonitoringTaskScheduler {

  private final MonitorableServer monitorable;
  private final ServerMonitoringService serverMonitoringService;
  private final ScheduledExecutorService executorService;

  public MonitoringTaskScheduler(MonitorableServer monitorable, ServerMonitoringService serverMonitoringService) {
    this.monitorable = monitorable;
    this.serverMonitoringService = serverMonitoringService;
    this.executorService = Executors.newSingleThreadScheduledExecutor();
  }

  public ScheduledFuture<?> start() {
    return executorService.scheduleAtFixedRate(monitorServerTask(), 0,
        monitorable.getServerConfiguration().getMonitoringFixedRate(), MILLISECONDS);
  }

  private Runnable monitorServerTask() {
    return () -> serverMonitoringService.monitorServer(monitorable);
  }

}
