package main;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        File file = new File("image");
        System.out.println(file.getParent());
        file = file.getAbsoluteFile();
        System.out.println(file.getParent());
    }
}