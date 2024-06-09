package agh.g3.services;

import agh.g3.model.Log;
import agh.g3.model.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public List<Log> last() {
        List<Log> logList = getLogList();
        List<Log> lastLogs = new ArrayList<>();
        int start = Math.max(0, logList.size() - 5);
        for (int i = start; i < logList.size(); i++) {
            Log log = logList.get(i);
            System.out.println("Index: " + i + ", Task Name: " + log.getTask().getName());
        }
        return lastLogs;
    }

    public void continueTask() {
        List<Log> logList = getLogList();
        try {
            Log log = logList.getLast();
            if (log.getStatus() == Status.STOP) {
                System.out.println("Starting last active job.");
                start(log);
            } else {
                System.out.println("Work already started.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred in the continueTask method.");
            e.printStackTrace();
        }
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
