package com.gzl0ng.nio.other.path;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author:郭正龙
 * @data:2022/12/14
 */
public class PathDemo1 {
    public static void main(String[] args) {
//        String path = PathDemo1.class.getResource("").getPath();
        String path = "D:/idea_work/nio/target/classes/com/gzl0ng/path";

        //创建path实例
        Path inputPath = Paths.get(path + "01.txt");

        //创建相对路径
        //代码1目录
        Path project = Paths.get(path, "/");
        //代码2文件
        Path file = Paths.get(path, "01.txt");

        //路径标准化
        path = "D:\\idea_work\\nio\\target\\classes\\com\\gzl0n\\path\\..\\001.txt";
        System.out.println(path);
        Path path1 = Paths.get(path);

        Path normalize = path1.normalize();
        System.out.println(normalize);
    }
}
