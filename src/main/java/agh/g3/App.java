package agh.g3;

import agh.g3.model.*;
import agh.g3.services.ReportService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static agh.g3.model.Log.FORMATTER;
public class App 
{
    public static void main( String[] args )
    {
        List<Log> logs = new ArrayList<>();

        logs.add(new Log(new Task("Task 1"), new Project("Project Alpha"), LocalDateTime.parse("2024-05-08 08:00:00", FORMATTER), Status.START));
        logs.add(new Log(new Task("Task 1"), new Project("Project Alpha"), LocalDateTime.parse("2024-05-08 10:00:00", FORMATTER), Status.STOP));
        logs.add(new Log(new Task("Task 1"), new Project("Project Alpha"), LocalDateTime.parse("2024-06-08 08:00:00", FORMATTER), Status.START));
        logs.add(new Log(new Task("Task 1"), new Project("Project Alpha"), LocalDateTime.parse("2024-06-08 10:00:00", FORMATTER), Status.STOP));
        logs.add(new Log(new Task("Task 2"), new Project("Project Gamma"), LocalDateTime.parse("2024-06-08 11:00:00", FORMATTER), Status.START));
        logs.add(new Log(new Task("Task 2"), new Project("Project Gamma"), LocalDateTime.parse("2024-06-08 13:00:00", FORMATTER), Status.STOP));

        ReportService reportService = new ReportService();
        Report report = reportService.generateReport(logs);
        reportService.printReport(report);
    }
}
