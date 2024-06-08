package agh.g3.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Task task;
    private Project project;
    private LocalDateTime time;
    private Enum<Status> status;

    public Log(Task task, Project project, Enum<Status> status) {
        this.task = task;
        this.project = project;
        this.time = LocalDateTime.now();
        this.status = status;
    }

    public Log(Task task, Project project, LocalDateTime time, Enum<Status> status) {
        this.task = task;
        this.project = project;
        this.time = time;
        this.status = status;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Enum<Status> getStatus() {
        return status;
    }

    public void setStatus(Enum<Status> status) {
        this.status = status;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = LocalDateTime.parse(time.format(FORMATTER));
    }

    @Override
    public String toString() {
        return "Log{" +
                "task=" + task +
                ", project=" + project +
                ", time='" + time + '\'' +
                ", status=" + status +
                '}';
    }
}
