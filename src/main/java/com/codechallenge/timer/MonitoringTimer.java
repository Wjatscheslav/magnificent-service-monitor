package com.codechallenge.timer;

import java.util.Timer;
import java.util.TimerTask;

import com.codechallenge.monitorable.MonitorableServer;
import com.codechallenge.service.ServerMonitoringService;

public class MonitoringTimer {

  private final MonitorableServer monitorable;
  private final ServerMonitoringService serverMonitoringService;
  private final Long monitoringFixedRate;

  public MonitoringTimer(MonitorableServer monitorable, ServerMonitoringService serverMonitoringService,
                         Long monitoringFixedRate) {
    this.monitorable = monitorable;
    this.serverMonitoringService = serverMonitoringService;
    this.monitoringFixedRate = monitoringFixedRate;
  }

  public void start() {
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(monitorServerTimerTask(), 0, monitoringFixedRate);
  }

  private TimerTask monitorServerTimerTask() {
    return new TimerTask() {
      @Override
      public void run() {
        serverMonitoringService.monitorServer(monitorable);
      }
    };
  }

}
