package agh.g3;

import agh.g3.model.Log;
import agh.g3.model.Project;
import agh.g3.model.Status;
import agh.g3.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static agh.g3.model.Log.FORMATTER;

public class TestingMain {
    public static void main(String[] args) {

        FileManager fileManager = new FileManager();
        List<Log> logs = fileManager.readFile();
        fileManager.printLogs(logs);

        Log log1 = new Log(new Task("Task 1"), new Project("Project Alpha"), LocalDateTime.parse("2024-05-08 08:00:00", FORMATTER), Status.START);
        Log log2 = new Log(new Task("Task 1"), new Project("Project Alpha"), LocalDateTime.parse("2024-05-08 10:00:00", FORMATTER), Status.STOP);
        Log log3 = new Log(new Task("Task 1"), new Project("Project Alpha"), LocalDateTime.parse("2024-06-08 08:00:00", FORMATTER), Status.START);
        Log log4 = new Log(new Task("Task 1"), new Project("Project Alpha"), LocalDateTime.parse("2024-06-08 10:00:00", FORMATTER), Status.STOP);
        Log log5 = new Log(new Task("Task 2"), new Project("Project Gamma"), LocalDateTime.parse("2024-06-08 11:00:00", FORMATTER), Status.START);
        Log log6 = new Log(new Task("Task 2"), new Project("Project Gamma"), LocalDateTime.parse("2024-06-08 13:00:00", FORMATTER), Status.STOP);

        fileManager.saveToFile(log1);
        fileManager.saveToFile(log2);
        fileManager.saveToFile(log3);
        fileManager.saveToFile(log4);
        fileManager.saveToFile(log5);
        fileManager.saveToFile(log6);
    }
}
