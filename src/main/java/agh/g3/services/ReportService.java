package agh.g3.services;

import agh.g3.model.Log;
import agh.g3.model.Report;
import agh.g3.model.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class ReportService {
    FileService fileService = new FileService();
    List<Log> currentLogList = fileService.getLogList();
    List<Log> logs = setTimeSpanLogs(readParameter("default"));

    public Report generateReport() {
        Report report = new Report();
        Map<String, LocalDateTime> taskStartTimes = new HashMap<>();

        for (Log log : logs) {
            String project = log.getProject().getName();
            String task = log.getTask().getName();
            LocalDateTime time = log.getTime();

            String uniqueTaskKey = project + ":" + task;

            if (log.getStatus() == Status.START) {
                taskStartTimes.put(uniqueTaskKey, time);
            } else if (log.getStatus() == Status.STOP) {
                LocalDateTime startTime = taskStartTimes.get(uniqueTaskKey);
                if (startTime != null) {
                    Duration duration = Duration.between(startTime, time);
                    report.addTaskDuration(project, task, duration);
                }
            }
        }
        return report;
    }

    public void printReport(Report report) {
        report.getProjectTaskDurations().forEach((project, tasks) -> {
            Duration projectTotal = tasks.values().stream().reduce(Duration.ZERO, Duration::plus);
            System.out.println(project + " ... " + formatDuration(projectTotal));
            tasks.forEach((task, duration) ->
                    System.out.println("-- " + task + "... " + formatDuration(duration))
            );
        });

        System.out.println("---\nTotal... " + formatDuration(report.getTotalDuration()));
    }

    private static String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = (duration.toMinutes() % 60);
        long seconds = (duration.getSeconds() % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private TimeSpan readParameter(String parametr) {
        var command = parametr.toLowerCase();
        var timeSpan = TimeSpan.DEFAULT;
        switch (command) {
            case "default" -> {return timeSpan;}
            case "today" -> timeSpan = TimeSpan.TODAY;
            case "yesterday" -> timeSpan = TimeSpan.YESTERDAY;
            case "current week" -> timeSpan = TimeSpan.CURRENT_WEEK;
            case "last week" -> timeSpan = TimeSpan.LAST_WEEK;
        }
        return timeSpan;
    }

    private List<Log> setTimeSpanLogs(TimeSpan timeSpan) {
        var outList = new ArrayList<Log>();
        var date = LocalDateTime.now().getDayOfYear();

        switch (timeSpan) {
            case DEFAULT -> outList.addAll(currentLogList);
            case TODAY -> {
                for (var log : currentLogList) {
                    var logDate = log.getTime().getDayOfYear();
                    if (logDate == date ) {
                        outList.add(log);
                    }
                }
            }
            case YESTERDAY -> {
                for (var log : currentLogList) {
                    var logDate = log.getTime().getDayOfYear() + 1;
                    if (logDate == date ) {
                        outList.add(log);
                    }
                }
            }
            case CURRENT_WEEK -> {
                for (var log : currentLogList) {
                    var logDate = log.getTime().getDayOfYear();
                    var dayOfWeek = log.getTime().getDayOfWeek().getValue();
                    if (date - logDate < (7 - dayOfWeek)) {
                        outList.add(log);
                    }
                }
            }
            case LAST_WEEK -> {
                for (var log : currentLogList) {
                    var logDate = log.getTime().getDayOfYear();
                    var dayOfWeek = log.getTime().getDayOfWeek().getValue();
                    if (date - logDate < (14 - dayOfWeek) && date - logDate > 7) {
                        outList.add(log);
                    }
                }
            }
        }
        return outList;
    }
}