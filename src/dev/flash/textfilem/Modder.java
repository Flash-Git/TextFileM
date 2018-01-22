package dev.flash.textfilem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Modder {

    private String title;
    private int width, height;

    //Folder in /res
    String toSort = "toSort";

    //Output in /output
    String outputFile = "Sorted + Merged";

    public Modder(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public void start() {
        try {
            appendFileData(toSort, outputFile);
        } catch(IOException e) {
            System.err.println(e);
        }
    }

    public void appendFileData(String folderName, String outputFile) throws IOException {
        deleteFile(outputFile);
        List<String> lines = new ArrayList<>();
        for(String file : getFolderData(folderName)) {
            lines.addAll(readTextFileByLines("res/" + folderName + "/" + file));
        }
        writeToTextFile("output/" + outputFile + ".txt", sortAddresses(lines));

        System.out.println("All Files in res/" + folderName + " sorted and merged into output/" + outputFile);
    }

    public void deleteFile(String outputFile) {
        File file = new File("output/" + outputFile + ".txt");

        if(file.delete()) {
            System.out.println("output/" + outputFile + ".txt deleted successfully");
        } else {
            System.out.println("Failed to delete output/" + outputFile + ".txt");
        }
    }

    public static List<String> readTextFileByLines(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        return lines;
    }

    public String[] getFolderData(String folderName) throws IOException {
        File f = new File("res/" + folderName);
        return f.list();
    }

    public String sortAddresses(List<String> lines) {
        int i = 0;
        while(i < lines.size()) {
            if(lines.get(i).length() < 133) {
                lines.remove(i);
            } else {
                i++;
            }
        }
        lines.sort(String.CASE_INSENSITIVE_ORDER);
        return String.join(" \n", lines);
    }

    public static void writeToTextFile(String fileName, String content) throws IOException {
        Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE);
    }

    public void getFilePath() throws IOException {//prints files
        Files.list(Paths.get(".")).forEach(System.out:: println);
    }

}
