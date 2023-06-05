package com.example.upload;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class deleteFile {
    public static void main(String[] args) {
        Path path = Paths.get("C:\\Users\\wanbo_pp\\Desktop\\aim\\cert");
        try {
            Files.walkFileTree(path,
                    new SimpleFileVisitor<Path>(){
                        @Override
                        public FileVisitResult visitFile(Path file,
                                                         BasicFileAttributes attrs) throws IOException {
                            Files.delete(file);
                            System.out.printf("文件被删除 : %s%n", file);
                            return FileVisitResult.CONTINUE;
                        }
                        // 再去遍历删除目录
                        @Override
                        public FileVisitResult postVisitDirectory(Path dir,
                                                                  IOException exc) throws IOException {
                            Files.delete(dir);
                            System.out.printf("文件夹被删除: %s%n", dir);
                            return FileVisitResult.CONTINUE;
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
