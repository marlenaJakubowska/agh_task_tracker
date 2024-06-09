package agh.g3.services;

import agh.g3.model.Log;
import agh.g3.model.Report;
import agh.g3.model.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {

    public Report generateReport(List<Log> logs) {
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
//            System.out.println(project + " ... " + formatDuration(projectTotal));
//            tasks.forEach((task, duration) ->
//                    System.out.println("-- " + task + "... " + formatDuration(duration))
//            );
            System.out.printf("--------------------------------%n");
            System.out.printf("| %-20s | %-10s |%n", project, formatDuration(projectTotal));
            System.out.printf("--------------------------------%n");

            tasks.forEach((task, duration) ->
//                    System.out.println("-- " + task + "... " + formatDuration(duration));
                    System.out.printf("| - %-18s | %-10s |%n", project, formatDuration(duration))
            );
            System.out.println();

        });

        System.out.println("---\nTotal... " + formatDuration(report.getTotalDuration()));
    }

    private static String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = (duration.toMinutes() % 60);
        long seconds = (duration.getSeconds() % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}