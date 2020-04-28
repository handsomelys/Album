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

    public void push(File f) {
        File next = this.getNext();
        if (next == null)
            this.dol.add(f);
        else
            if (!f.equals(next)) {
                removeLater(this.dol, current);
                this.dol.add(f);
            }
        ++current;
    }
    public void rewind() {
        --current;
    }

    public File getNext() {
        if (this.dol.isEmpty() || current >= this.dol.size()) 
            return null;
        else
            return this.dol.get(current);
    }
    public File getPrior() {
        if (this.dol.isEmpty() || current <= 1)
            return null;
        else
            return this.dol.get(current-2);
    }

    public static boolean removeLater(ArrayList<?> l, int index) {
        boolean result = true;
        for (int i = l.size()-1; i > index; --i)
            result &= (boolean)l.remove(i);
        return result;
    }
}