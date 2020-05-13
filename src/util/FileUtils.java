package util;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
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
        String reg = "-copy(\\((\\d+)\\))?";
        Pattern p = Pattern.compile(reg);
        String name = file.getName();
        File nf = Path.of(dest, name).toFile();
        // getting an unique name for copied file
        while (true) {
            if(nf.exists()) {
                Matcher m = p.matcher(name);
                if(m.find()) {
                    // had found the copied format inside file name
                    if (m.group(2) != null) {
                        int c = Integer.parseInt(m.group(2));
                        name = m.replaceFirst(String.format("-copy(%d)", c+1));
                    } else {
                        name = m.replaceFirst("-copy(1)");
                    }
                }
                else {
                    // did not find the copied format, adding it
                    if ((name != null) && (name.length() > 0)) {
                        int dot = name.lastIndexOf('.');
                        if (dot > 0 && dot < (name.length()-1)) {
                            String s = name.substring(0, dot);
                            name = s + "-copy." + name.substring(dot+1);
                        } else
                            name += "-copy";
                    }
                }
                nf = Path.of(dest, name).toFile();
                continue;
            }
            break;
        }
        try {
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
        for (File file: files) {
            FileUtils.copyFile(file, dest);
        }
    }

    /**
     * @param file file to rename
     * @param name only name
     */
    public static boolean renameFile(File file, String name) {
        File nf = Path.of(file.getParent(), name).toFile();
        if (nf.exists()) {
            return false;
        } else {
            file.renameTo(nf);
            return true;
        }
    }
    /**
     * @param files file to rename
     * @param name name prefix
     * @param start index that start from
     * @param bit the bits of later number
     */
    public static void renameFiles(ArrayList<File> files,
        String name, int start, int bit) {
        for (int i = 0; i < files.size(); ++i) {
            String previous = files.get(i).getName();
            String suffix = previous.substring(previous.lastIndexOf("."));
            FileUtils.renameFile(files.get(i),
                name + String.format("%0"+bit+"d", i) + suffix);
        }
    }

    /**
     * @param file file to delete
     */
    public static void removeFile(File file) {
        if (file.exists() && file.isFile()) {
        	
        	file.delete();
        }
            
    }
    /**
     * @param files files to delete
     */
    public static void removeFiles(ArrayList<File> files) {
        for (File file: files)
            FileUtils.removeFile(file);
    }
    /**
     * obvious
     */
    public static boolean isPicture(File f) {
        String regex = ".*.(jpg|jpeg|png|bmp|gif)$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return p.matcher(f.getName()).matches();
    }
    /**
     * obvious
     */
    public static int getPicturesCount(File directory) {
        int count = 0;
        for (File f: directory.listFiles())
            count += isPicture(f)? 1: 0;
        return count;
    }
    /**
     * obvious
     */
    public static long getPicturesSize(File directory) {
        long total = 0;
        for (File f: directory.listFiles())
            total += isPicture(f)? f.length(): 0;
        return total;
    }
    /**
     * obvious
     */
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
    /**
     * obvious
     */
    public static String getFileNameNoExtension(File file) {
        String name = file.getName();
        return name.substring(0, name.lastIndexOf("."));
	}
}