package com.gapco.backend.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class FileLogger {

    private String path;

    public FileLogger(String path) {
        this.path = path;
    }

    public void createLog(String contentHeader, String contentBody) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
            DateFormat dateFormat2 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            Date date = new Date();
            String data = dateFormat2.format(date) + " " + contentHeader + " - " + contentBody + "\n";
            String fileName = dateFormat.format(date) + ".txt";

            Optional<File> optionalFile = returnFile(this.path+fileName);

            if(optionalFile.isPresent()){

                File f1 = optionalFile.get();

                FileWriter fw = new FileWriter(f1, true);

                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(data); //Writing to the file

                bw.close(); //Close the BufferedWriter

                fw.close();

            } else {
                System.out.println("File instance is null");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Optional<File> returnFile(String target){

        File file = null;
        Path path = Paths.get(target);
        Path parent = path.getParent();

        try{
            if(parent != null && Files.notExists(parent)) {
                Files.createDirectories(parent);
            }
            if(Files.notExists(path)) {
                file = Files.createFile(path).toFile();
            } else {
                file = path.toFile();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return Optional.ofNullable(file);
    }
}
