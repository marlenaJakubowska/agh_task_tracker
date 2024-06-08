package agh.g3.services;

import agh.g3.model.Log;
import agh.g3.model.Project;
import agh.g3.model.Status;
import agh.g3.model.Task;

public class LogService {
    public Log start(Task task, Project project) {
        Log newLog = new Log(task, project, Status.START);
        System.out.println("Starting timer...");
        System.out.println("Log saved: " + newLog.getTime() + " " + newLog.getTask() + " " + newLog.getProject() + " " + newLog.getStatus());
        return newLog;
    }

    public Log stop(Log log) {
        Log newLog = new Log(log.getTask(), log.getProject(), Status.STOP);
        System.out.println("Stopping timer...");
        System.out.println("Log saved: " + log.getTime() + " " + log.getTask() + " " + log.getProject() + " " + log.getStatus());
        return newLog;
    }

    public Log continueWork(Log log) {
        Log newLog = new Log(log.getTask(), log.getProject(), Status.START);
        System.out.println("Continuing timer...");
        System.out.println("Log saved: " + log.getTime() + " " + log.getTask() + " " + log.getProject() + " " + log.getStatus());
        return newLog;
    }
}
