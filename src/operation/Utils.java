package operation;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Utils {
    /**
     * @param files
     *        Files to copy.
     * @param dest
     *        The desination path.
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
            Utils.copyFile(f, dest);
        }
    }

    /**
     * @param file
     *        File to rename.
     * @param name
     *        Only name.
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
                Utils.renameFile(files.get(i),
                    name + String.format("%0"+bit+"d", i) + suffix);
            }
    }
    public static void main(String args[]) {
        String path = "C:\\Users\\lsn\\Desktop\\New folder\\WeChat Screenshot_20200304225843.png";
        String dest = "C:\\Users\\lsn\\Desktop\\New folder (2)";
        ArrayList<File> a = new ArrayList<>();
        File f = new File(path);
        File nf = new File("C:\\Users\\lsn\\Desktop\\New folder (2)\\WeChat Screenshot_20200304225843.png");
        a.add(f);
        Utils.copyFiles(a, dest);
        a.remove(f);
        a.add(nf);
        Utils.renameFiles(a, "good", 1, 4);
    }
}