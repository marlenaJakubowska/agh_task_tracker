package agh.g3;

import agh.g3.model.Log;
import agh.g3.model.Project;
import agh.g3.model.Status;
import agh.g3.model.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FileManager {

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String headers;
    private ArrayList<Log> logList = new ArrayList<>();
    public static final String FILE_PATH = "";

    //Method that loads a data file
    private Path loadFile() {
        return Paths.get(FILE_PATH);
    }

    //Method that loads subsequent lines as String
    public ArrayList loadLines() {
        Path path = loadFile();
        ArrayList readedFile = new ArrayList<>();
        try {
            readedFile = (ArrayList) Files.readAllLines(path);
        } catch (IOException ex) {
            System.out.println("File does not exist");
        }

        //Create header and remove from readed list
        headers = (String) readedFile.getFirst();
        readedFile.removeFirst();

        ArrayList logs = toObject(readedFile);
        return logs;
    }

    // The method retrieves a series of data corresponding to lines in the file
    // and then creates Log objects and fills them with data

    public ArrayList<Log> toObject(ArrayList<String> readedFile) {
        ArrayList<Log> logs = new ArrayList<>();
        for (String line : readedFile) {
            String[] l = line.split(";");
            Task task = new Task(l[0]);
            Project project = new Project(l[1]);
            LocalDateTime time = LocalDateTime.parse(l[2], FORMATTER);
            Status status = Status.valueOf(l[3]);
            Log log = new Log(task, project, time, status);
            logs.add(log);
        }
        return logs;
    }
}
