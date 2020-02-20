package com.epam.izh.rd.online.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path){
        File dir = new File(path);
        long count = 0;
        for (File f : dir.listFiles()) {
            if (f.isFile()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        File dir = new File(path);
        long count = 1;
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to){
        Path path = Paths.get(from);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*.txt")) {
            for (Path entry: stream) {
                Files.copy(entry , Paths.get(to + "\\" + entry.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        Path dir = Paths.get(path + "/" + name);
        try {
            Files.createFile(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Files.exists(dir)){
            return true;
        }
        return false;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        String result = "";
        Path path = Paths.get("src/main/resources/" + fileName);
        try {
            List<String> strings = Files.readAllLines(path);
            for (String line : strings) {
                result += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
