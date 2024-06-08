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
    public static final String DELIMITER = ";";

    //Method that loads a data file
    private Path loadPath() {
        return Paths.get(FILE_PATH);
    }

    //Method that loads subsequent lines as String
    public ArrayList readFile() {
        var path = loadPath();
        ArrayList readedLines = new ArrayList<>();
        try {
            readedLines = (ArrayList) Files.readAllLines(path);
        } catch (IOException ex) {
            System.out.println("File does not exist");
        }

        //Create header and remove from readed list
        headers = (String) readedLines.getFirst();
        readedLines.removeFirst();

        ArrayList logs = toLogObject(readedLines);
        return logs;
    }

    // The method retrieves a series of data corresponding to lines in the file
    // and then creates Log objects and fills them with data

    private ArrayList<Log> toLogObject(ArrayList<String> readedFile) {
        ArrayList<Log> logs = new ArrayList<>();
        for (var line : readedFile) {
            String[] l = line.split(DELIMITER);
            var task = new Task(l[0]);
            var project = new Project(l[1]);
            var time = LocalDateTime.parse(l[2], FORMATTER);
            var status = Status.valueOf(l[3]);
            var log = new Log(task, project, time, status);
            logs.add(log);
        }
        return logs;
    }

    public void saveToFile() {
        ArrayList outList = new ArrayList<>();
        outList.add(headers);
        for (var element : logList) {
            StringBuilder sb = new StringBuilder();
            sb.append(element.getTask().getName()).
                    append(DELIMITER).
                    append(element.getProject().getName()).
                    append(DELIMITER).
                    append(element.getTime().format(FORMATTER)).
                    append(DELIMITER).
                    append(element.getStatus().toString());
            outList.add(sb);
        }
        try {
            Files.write(loadPath(), outList);
        } catch (IOException ex) {
            System.out.println("Unfortunately, the file creation failed");
        }
    }
}
