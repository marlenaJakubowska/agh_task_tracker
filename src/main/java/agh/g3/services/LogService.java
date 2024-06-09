package agh.g3.services;

import agh.g3.FileManager;
import agh.g3.model.Log;
import agh.g3.model.Project;
import agh.g3.model.Status;
import agh.g3.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public void printLogs(List<Log> logs) {
        logs.sort(Comparator.comparing(Log::getTime).reversed());
        Map<String, List<Duration>> durations = calculateTaskDurations(logs);

        for (Log log : logs) {
            if (log.getStatus() == Status.START) {
                String key = log.getTask().getName() + "-" + log.getProject().getName();
                List<Duration> durationsList = durations.getOrDefault(key, List.of(Duration.ZERO));
                Duration totalDuration = durationsList.isEmpty() ? Duration.ZERO : durationsList.get(0);
                String formattedDuration = formatDuration(totalDuration);
                System.out.println("Task_name: " + log.getTask().getName() +
                        ", Project_name: " + log.getProject().getName() +
                        ", Time_started: " + log.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                        ", Duration: " + formattedDuration);
                if (!durationsList.isEmpty()) durationsList.remove(0);
            }
        }
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = (duration.toMinutes() % 60);
        long seconds = (duration.getSeconds() % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public Map<String, List<Duration>> calculateTaskDurations(List<Log> logs) {
        Map<String, Queue<LocalDateTime>> startTimes = new HashMap<>();
        Map<String, List<Duration>> taskDurations = new HashMap<>();

        logs.sort(Comparator.comparing(Log::getTime));

        for (Log log : logs) {
            String key = log.getTask().getName() + "-" + log.getProject().getName();
            if (log.getStatus() == Status.START) {
                startTimes.computeIfAbsent(key, k -> new LinkedList<>()).add(log.getTime());
            } else if (log.getStatus() == Status.STOP && startTimes.containsKey(key) && !startTimes.get(key).isEmpty()) {
                LocalDateTime startTime = startTimes.get(key).poll();
                if (startTime != null) {
                    Duration duration = Duration.between(startTime, log.getTime());
                    taskDurations.computeIfAbsent(key, k -> new ArrayList<>()).add(duration);
                }
            }
        }
        return taskDurations;
    }
}
