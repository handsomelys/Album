package operation;

import java.io.File;
import java.util.ArrayList;

public class DirectoryOperationList {
    public ArrayList<File> dol;
    public int current;
    public DirectoryOperationList() {
        this.dol = new ArrayList<File>();
        this.current = 0;
    }
    public boolean push(File f) {
        boolean result = true;
        if (this.dol.size() > current && !this.dol.get(current).equals(f)) {
            removeLater(this.dol, current);
            this.dol.add(f);
            result = false;
        }
        ++current;
        return result;
    }
    public File next() {
        if (current > this.dol.size()) 
            return null;
        ++current;
        return this.dol.get(current-1);
    }
    public File prior() {
        if (this.dol.isEmpty() && current < 1)
            return null;
        --current;
        return this.dol.get(current-1);
    }
    public String status() {
        if (this.dol.isEmpty())
            return "nothing";
        else if (this.dol.size() > current && current == 1)
            return "head";
        else if (1 < current && current < this.dol.size())
            return "middle";
        else if (this.dol.size() == current && current > 1)
            return "tail";
        else if (this.dol.size() == 1 && current == this.dol.size())
            return "single";
        return null;
    }
    public static boolean removeLater(ArrayList<?> l, int index) {
        boolean result = true;
        for (int i = l.size()-1; i > index; --i)
            result &= (boolean)l.remove(i);
        return result;
    }
}