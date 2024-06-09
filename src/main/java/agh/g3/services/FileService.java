package agh.g3.services;

import agh.g3.model.Log;
import agh.g3.model.Project;
import agh.g3.model.Status;
import agh.g3.model.Task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    public static final String FILE_PATH;

    static {
        try {
            FILE_PATH = new File("./taskTracker.csv").getCanonicalPath();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String DELIMITER = ";";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss");
    private String headers;
    private List<Log> logList = new ArrayList<>();


    private Path loadPath() {
        return Paths.get(FILE_PATH);
    }

    private List<Log> toLogObject(List<String> readFile) {
        var logs = new ArrayList<Log>();
        for (var line : readFile) {
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

    //Method that reads list of Logs from CSV file
    public List<Log> readFile() {
        var path = loadPath();
        var readLines = new ArrayList<String>();
        try {
            readLines = (ArrayList) Files.readAllLines(path);
        } catch (IOException ex) {
            System.out.println("File does not exist");
        }

        if (!readLines.isEmpty()) {
            headers = readLines.getFirst();
            readLines.removeFirst();
        }

        logList = toLogObject(readLines);
        return logList;
    }

    //Method that loads list of Logs to CSV file
    public void saveToFile(List<Log> logsToSave) {
        var outList = new ArrayList<String>();
        for (var log : logsToSave) {
            StringBuilder sb = new StringBuilder();
            sb.append(log.getTask()
                            .getName()).
                    append(DELIMITER).
                    append(log.getProject()
                            .getName()).
                    append(DELIMITER).
                    append(log.getTime()
                            .format(FORMATTER)).
                    append(DELIMITER).
                    append(log.getStatus()
                            .toString());
            outList.add(String.valueOf(sb));
        }

        extendFile(outList);
    }

    private void extendFile(List<String> outList) {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for (var element : outList) {
                pw.println(element);
            }
            pw.close();
        } catch (IOException ex) {
            System.out.println("Unfortunately, the file creation failed");
        }
    }

    //Method that prints list of Logs
    public void printLogs(List<Log> logs) {
        for (var log : logs) {
            var sb = new StringBuilder();
            sb.append(log.getTask()
                            .getName()).
                    append(DELIMITER).
                    append(log.getProject()
                            .getName()).
                    append(DELIMITER).
                    append(log.getTime()
                            .format(FORMATTER)).
                    append(DELIMITER).
                    append(log.getStatus()
                            .toString());
            System.out.println(sb);
        }
    }

    public List<Log> getLogList() {
        return logList;
    }
}
