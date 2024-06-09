package agh.g3.services;

import agh.g3.model.Log;
import agh.g3.model.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LogService {

    FileService fileManager = new FileService();

    public List<Log> getLogList() {
        return fileManager.readFile();
    }

    public void start(Log log) {
        List<Log> newLogList = new ArrayList<>();
        try {
            List<Log> logList = getLogList();
            if (!logList.isEmpty()) {
                Log lastLog = logList.getLast();
                if (lastLog.getStatus() == Status.START) {
                    System.out.println("Work already started. Stopping.");
                    stop();
                }
            }
            System.out.println("Starting timer...");
            newLogList.add(log);
            fileManager.saveToFile(newLogList);
            System.out.println("Log saved: " + log.getTime() + " " + log.getTask() + " " + log.getProject() + " " + log.getStatus());
        } catch (Exception e) {
            System.out.println("An error occurred in the start method.");
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            List<Log> logList = getLogList();
            List<Log> newLogList = new ArrayList<>();
            Log log = logList.getLast();
            if (log.getStatus() == Status.START) {
                Log newLog = new Log(log.getTask(), log.getProject(), Status.STOP);
                System.out.println("Stopping timer...");
                newLogList.add(newLog);
                fileManager.saveToFile(newLogList);
                System.out.println("Log saved: " + newLog.getTime() + " " + newLog.getTask() + " " + newLog.getProject() + " " + newLog.getStatus());
            } else {
                System.out.println("No started task to stop.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Task list is empty. Start a task first.");
            e.printStackTrace();
        }
    }
}