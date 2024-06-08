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
                System.out.println("show help");
                break;
            default:
                System.out.println("Use help command to see list of available commands");
        }
    }
}
