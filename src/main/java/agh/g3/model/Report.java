package agh.g3.model;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Report {
    private Map<String, Map<String, Duration>> projectTaskDurations = new HashMap<>();
    private Duration totalDuration = Duration.ZERO;

    public void addTaskDuration(String project, String task, Duration duration) {
        projectTaskDurations.putIfAbsent(project, new HashMap<>());
        projectTaskDurations.get(project).merge(task, duration, Duration::plus);
        totalDuration = totalDuration.plus(duration);
    }

    public Map<String, Map<String, Duration>> getProjectTaskDurations() {
        return projectTaskDurations;
    }

    public Duration getTotalDuration() {
        return totalDuration;
    }
}
