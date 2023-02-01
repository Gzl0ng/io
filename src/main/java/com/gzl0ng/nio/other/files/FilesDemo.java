package com.gzl0ng.nio.other.files;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author:郭正龙
 * @data:2022/12/14
 */
public class FilesDemo {
    public static void main(String[] args) {
        //createDirectory创建目录
        Path path = Paths.get("E:\\tmp\\gzl");
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //FileCopy
    @Test
    public void copy(){
        Path path = Paths.get("E:\\tmp\\gzl\\01.txt");
        Path pat2 = Paths.get("E:\\tmp\\gzl\\02.txt");

        try {
            Files.copy(path,pat2, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //moveFile
    @Test
    public void mv(){
        Path path = Paths.get("E:\\tmp\\gzl\\01.txt");
        Path pat2 = Paths.get("E:\\tmp\\gzl\\01test.txt");

        try {
            Files.move(path,pat2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //deleteFile
    @Test
    public void del(){
        Path path = Paths.get("E:\\tmp\\gzl\\01test.txt");

        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //查找一个路径下某个文件
    @Test
    public void test(){
        Path path = Paths.get("E:\\tmp\\gzl");
        String fileToFind = File.separator + "02.txt";

        try {
            Files.walkFileTree(path,
                    new SimpleFileVisitor<Path>(){
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            String fileString = file.toAbsolutePath().toString();
//                            System.out.println("pathString:" + fileString);

                            if (fileString.endsWith(fileToFind)){
                                System.out.println("file found at path:" + file.toAbsolutePath());
                                return FileVisitResult.TERMINATE;
                            }
                            return FileVisitResult.CONTINUE;
                        }
                    });
        } catch (IOException e) {
        e.printStackTrace();
        }
    }
}
