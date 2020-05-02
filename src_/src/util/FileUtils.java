package util;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    /**
     * @param files files to copy
     * @param dest the desination path
     */
    public static void copyFile(File f, String dest) {
        try {
            File nf = Path.of(dest, f.getName()).toFile();
            if(!nf.exists())
                Files.copy(f.toPath(), nf.toPath());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void copyFiles(ArrayList<File> files, String dest) {
        for (File f: files) {
            FileUtils.copyFile(f, dest);
        }
    }

    /**
     * @param file file to rename
     * @param name only name
     */
    public static void renameFile(File file, String name) {
        File nf = Path.of(file.getParent(), name).toFile();
        if (nf.exists()) {
            System.out.println("file exist!");
        } else {
            file.renameTo(nf);
        }
    }
    /**
     * write it later
     * @param args
     */
    public static void cutFile(File f, String dest) { } 
    public static void deleteFile(File f) { }

    public static void renameFiles(ArrayList<File> files, String name,
        int start, int bit) {
            for (int i = 0; i < files.size(); ++i) {
                String previous = files.get(i).getName();
                String suffix = previous.substring(previous.lastIndexOf("."));
                FileUtils.renameFile(files.get(i),
                    name + String.format("%0"+bit+"d", i) + suffix);
            }
    }
    public static boolean isPicture(File f) {
        String regex = ".*.(jpg|jpeg|png|bmp|gif)$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return p.matcher(f.getName()).matches();
    }
    public static int getPicturesCount(File directory) {
        int count = 0;
        for (File f: directory.listFiles())
            count += isPicture(f)? 1: 0;
        return count;
    }
    public static long getPicturesSize(File directory) {
        long total = 0;
        //System.out.println(directory.exists());
        //System.out.println(directory.isDirectory());
        //System.out.println(directory.isFile());
        for (File f: directory.listFiles())
            total += isPicture(f)? f.length(): 0;
        return total;
    }
    public static String sizeToString(long size) {
        String[] sign = {"Byte", "KB", "MB", "GB", "TB"};
        int index = 0;
        double length = size;
        while (length > 1024) {
            length /= 1024;
            ++index;
        }
        return String.format("%.2f%s", length, sign[index]);
    }
    public static void main(String args[]) {
        String path = "C:\\Users\\lsn\\Desktop\\New folder\\WeChat Screenshot_20200304225843.png";
        String dest = "C:\\Users\\lsn\\Desktop\\New folder (2)";
        ArrayList<File> a = new ArrayList<>();
        File f = new File(path);
        File nf = new File("C:\\Users\\lsn\\Desktop\\New folder (2)\\WeChat Screenshot_20200304225843.png");
        a.add(f);
        FileUtils.copyFiles(a, dest);
        a.remove(f);
        a.add(nf);
        FileUtils.renameFiles(a, "good", 1, 4);
    }
}