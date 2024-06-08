package agh.g3;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
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
