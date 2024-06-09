package agh.g3;

import agh.g3.model.*;
import agh.g3.services.FileService;
import agh.g3.services.LogService;
import agh.g3.services.ReportService;
import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        FileService fileManager = new FileService();
        ReportService reportService = new ReportService();
        List<Log> logs = fileManager.readFile();

        Report report = reportService.generateReport(logs);
        reportService.printReport(report);

        LogService logService = new LogService();
        logService.printLogs(logs);

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
                System.out.println("Usage:");
                System.out.println("  start -t [task] -p [project]         \t Start time tracking for a task.");
                System.out.println("  stop                                 \t Stop the current task.");
                System.out.println("  continue                             \t Resume the last tracked task.");
                System.out.println("  report                               \t Show a summary report of all tracked tasks.");
                System.out.println("  list                                 \t List all task logs.");
                System.out.println("  last                                 \t Display the last 5 unique tasks.");
                System.out.println("  help                                 \t Show this help menu.");
                System.out.println("  help [command]                       \t Get help for a specific command.");
                System.out.println("\nExample:");
                System.out.println("  help start                           \t Provides more information on the 'start' command.");

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
