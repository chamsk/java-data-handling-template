package com.epam.izh.rd.online.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class SimpleFileRepository implements FileRepository {
    public final String RESOURCES_PATH = "src\\main\\resources\\";

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path){
        VisitorImpl visitor = new VisitorImpl();
        if(!Files.exists(Paths.get(path))){
            path =  RESOURCES_PATH + path;
        }
        try {
            Files.walkFileTree(Paths.get(path),visitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return visitor.countFiles;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        VisitorImpl visitor = new VisitorImpl();
        if(!Files.exists(Paths.get(path))){
            path = RESOURCES_PATH + path;
        }
        try {
            Files.walkFileTree(Paths.get(path),visitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return visitor.countDirs;
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
        Path fileName = Paths.get(path + "/" + name);
        try {
            if(Files.notExists(Paths.get(path))) {
                Files.createDirectory(Paths.get(path));
            }
            if(Files.notExists(fileName)) {
                Files.createFile(fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Files.exists(fileName);
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
        Path path = Paths.get(RESOURCES_PATH + fileName);
        try {
            List<String> strings = Files.readAllLines(path);
            for (String line : strings) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static class VisitorImpl extends SimpleFileVisitor<Path>
    {
        int countFiles=0;
        int countDirs=0;

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
        {
            if (attrs.isDirectory())
                countDirs++;
            return super.preVisitDirectory(dir, attrs);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
        {
            countFiles++;
            return FileVisitResult.CONTINUE;
        }
    }

}
