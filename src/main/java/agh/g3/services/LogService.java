package agh.g3.services;

import agh.g3.model.Log;
import agh.g3.model.Project;
import agh.g3.model.Status;
import agh.g3.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LogService {

    FileService fileManager = new FileService();
    List<Log> logList = fileManager.getLogList();

    public void start(Log log) {
        List<Log> newLogList = new ArrayList<>();
        try {
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

    public static void main(String[] args) {
        LogService logService = new LogService();
        Log log = new Log(new Task("Task 1"), new Project("Project Alpha"), Status.START);
        logService.start(log);
//        logService.stop();
//        logService.last();
//        logService.continueTask("0");
    }
}