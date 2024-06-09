package agh.g3;

import agh.g3.model.Log;
import agh.g3.model.Project;
import agh.g3.model.Status;
import agh.g3.model.Task;
import agh.g3.services.FileService;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static agh.g3.model.Log.FORMATTER;

public class TestingMain {
    public static void main(String[] args) {

        var fileService = new FileService();
        var logs = fileService.readFile();
        fileService.printLogs(logs);

//        var logsList = new ArrayList<Log>();
//        logsList.add(new Log(new Task("Task 1"), new Project("Project Alpha"), LocalDateTime.parse("2024-05-08 08:00:00", FORMATTER), Status.START));
//        logsList.add(new Log(new Task("Task 1"), new Project("Project Alpha"), LocalDateTime.parse("2024-05-08 10:00:00", FORMATTER), Status.STOP));
//        logsList.add(new Log(new Task("Task 1"), new Project("Project Alpha"), LocalDateTime.parse("2024-06-08 08:00:00", FORMATTER), Status.START));
//        logsList.add(new Log(new Task("Task 1"), new Project("Project Alpha"), LocalDateTime.parse("2024-06-08 10:00:00", FORMATTER), Status.STOP));
//        logsList.add(new Log(new Task("Task 2"), new Project("Project Gamma"), LocalDateTime.parse("2024-06-08 11:00:00", FORMATTER), Status.START));
//        logsList.add(new Log(new Task("Task 2"), new Project("Project Gamma"), LocalDateTime.parse("2024-06-08 13:00:00", FORMATTER), Status.STOP));
//
//        fileService.saveToFile(logsList);
    }
}
