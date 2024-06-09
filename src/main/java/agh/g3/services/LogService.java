package agh.g3.services;

import agh.g3.FileManager;
import agh.g3.model.Log;
import agh.g3.model.Status;

import java.util.List;

public class LogService {

    FileManager fileManager = new FileManager();
    List<Log> logList = fileManager.getLogList();

    public void start(Log log) {
        try {
            Log lastLog = logList.get(logList.size() - 1);
            if (lastLog.getStatus() == Status.START) {
                System.out.println("Work already started. Stopping.");
                stop(lastLog);
            }
            System.out.println("Starting timer...");
            fileManager.saveToFile(log);
            System.out.println("Log saved: " + log.getTime() + " " + log.getTask() + " " + log.getProject() + " " + log.getStatus());
        } catch (Exception e) {
            System.out.println("An error occurred in the start method.");
            e.printStackTrace();
        }
    }

    public void stop(Log log) {
        try {
            if (log.getStatus() != Status.STOP) {
                log.setStatus(Status.STOP);
            } else {
                log.setStatus(Status.STOP);
            }
            System.out.println("Stopping timer...");
            fileManager.saveToFile(log);
            System.out.println("Log saved: " + log.getTime() + " " + log.getTask() + " " + log.getProject() + " " + log.getStatus());
        } catch (Exception e) {
            System.out.println("An error occurred in the stop method.");
            e.printStackTrace();
        }
    }
}