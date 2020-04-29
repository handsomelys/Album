package operation;

import java.io.File;
import java.util.ArrayList;

public class Operation {
    public String type;
    public ArrayList<File> files;
    public String dest; // for copy, paste, cut, etc
    public String name; // for rename
    public int start; // for rename
    public int bit; // for rename
}