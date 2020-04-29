package operation;

import java.util.ArrayList;

public class FileOperationList {

    public ArrayList<FileOperation> dol;
    public int current;

    public FileOperationList() {
        this.dol = new ArrayList<FileOperation>();
        this.current = 0;
    }

    public void push(FileOperation f) {
        FileOperation next = this.getNext();
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

    public FileOperation getNext() {
        if (this.dol.isEmpty() || current >= this.dol.size()) 
            return null;
        else
            return this.dol.get(current);
    }
    public FileOperation getPrior() {
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