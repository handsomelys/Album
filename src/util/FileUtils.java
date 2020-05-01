package util;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    /**
     * @param file file to copy
     * @param dest the desination path
     */
    public static void copyFile(File file, String dest) {
        try {
            File nf = Path.of(dest, file.getName()).toFile();
            if(!nf.exists())
                Files.copy(file.toPath(), nf.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @param files files to copy
     * @param dest the desination path
     */
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
     * @param files file to rename
     * @param name name prefix
     * @param start index that start from
     * @param bit the bits of later number
     */
    public static void renameFiles(ArrayList<File> files, String name,
        int start, int bit) {
            for (int i = 0; i < files.size(); ++i) {
                String previous = files.get(i).getName();
                String suffix = previous.substring(previous.lastIndexOf("."));
                FileUtils.renameFile(files.get(i),
                    name + String.format("%0"+bit+"d", i) + suffix);
            }
    }
    public static void removeFile(File file) {
        if (file.exists() && file.isFile())
            file.delete();
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
}