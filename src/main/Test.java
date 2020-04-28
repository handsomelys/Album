package main;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        File f = new File("D:\\picture");
        System.out.println(f.isDirectory());
        System.out.println(f.getPath());
        System.out.println(f.getParentFile());
    }
}