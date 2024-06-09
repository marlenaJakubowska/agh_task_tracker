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

        if(args.length == 0){
            System.out.println("Please use one of the available commands or use help command to see list of available commands");
            return;
        }

        switch (args[0]) {
            case "start":
                System.out.println("starting task");
                break;
            case "stop":
                System.out.println("stopping task");
                break;
            case "continue":
                System.out.println("continue task");
                break;
            case "report":
                System.out.println("printing report");
                break;
            case "list":
                System.out.println("listing last logs");
                break;
            case "last":
                System.out.println("last 5 unique tasks");
                break;
            case "help":
                System.out.println("Available commands in Task Tracker:");
                System.out.println("*Save start time for the task:");
                System.out.println("start -t [task_name] -p [project_name]");
                System.out.println("*Save stop time for the task:");
                System.out.println("stop");
                System.out.println("*Continue last task:");
                System.out.println("continue");
                System.out.println("*Show report:");
                System.out.println("report");
                System.out.println("*Show list of logs:");
                System.out.println("list");
                System.out.println("*Show last 5 unique tasks:");
                System.out.println("last");
                System.out.println("*Show help menu:");
                System.out.println("help");
                System.out.println("For more information about specific command use help [specific command]");

                if(args.length>1){
                    switch (args[1]) {
                        case "continue":
                            System.out.println("Command used without arguments starts last task.");
                            System.out.println("Command used in format continue [number] starts task with index-number from list of last tasks");
                            break;
                        case "report":
                            System.out.println("Command used without arguments shows all the data.");
                            System.out.println("Command used with argument -p [project_name] shows time of tasks in project");
                            System.out.println("Command used with specific time argument shows ");
                            break;
                    }
                }

                break;
            default:
                System.out.println("Use help command to see list of available commands");
        }

    }
}
