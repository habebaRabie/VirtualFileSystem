import java.io.File;
import java.util.ArrayList;

public class Directory {
    private String name;
    private Directory parent;
    private String directoryPath;
    private ArrayList<Files> files = new ArrayList<>();
    private ArrayList<Directory> subDirectories = new ArrayList<>();
    private boolean deleted;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public void setParent(Directory dic) {
        this.parent = dic;
    }

    public Directory getParent() {
        return this.parent;
    }

    public ArrayList<Files> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<Files> files) {
        this.files = files;
    }

    public void addDirectory(Directory d) {
        this.subDirectories.add(d);
    }

    public void addFile(Files f) {
        this.files.add(f);
    }

    public ArrayList<Directory> getSubDirectories() {
        return subDirectories;
    }

    public void setSubDirectories(ArrayList<Directory> subDirectories) {
        this.subDirectories = subDirectories;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean printDirectoryStructure() {
        System.out.println("<" + this.name + ">");
        if (this.subDirectories.size() == 0 && this.getFiles().size() == 0) {
            return true;
        } else {
            ArrayList<Files> f = this.getFiles();
            for (Files file : f) {
                String[] splited = file.getPath().split("/");
                for (int i = 0; i < splited.length - 1; ++ i) System.out.print("\t");
                System.out.println(file.getFileName());
            }
            if (this.subDirectories.size() > 0) {
                ArrayList<Directory> d = this.getSubDirectories();
                for (Directory dir : d) {
                    String[] splited = dir.getDirectoryPath().split("/");
                    for (int i = 0; i < splited.length - 1; ++ i) System.out.print("\t");
                    dir.printDirectoryStructure();
                }
            }
        }
        return false;
    }

    public Files findFile(String path) {
        String[] pathSplit = path.split("/");
        Directory d = this;
        int i = 1;
        while (i < pathSplit.length - 1) {
            ArrayList<Directory> sub = d.getSubDirectories();
            for (Directory dir : sub) {
                if (dir.getName().equals(pathSplit[i])) {
                    ++ i;
                    d = dir;
                    break;
                }
            }
        }
            ArrayList<Files> f = d.getFiles();
        for (Files fs:f){
            if (fs.getPath().equals(path)) {
                return fs;
            }
        }
        return null;
    }

    public boolean printFiles() {
        ArrayList<Files> fs = this.getFiles();
        if (this.getFiles().size() == 0) {
            return true;
        }
        for (Files f : fs) {
            if (f.getAllocationAlgorithm() == 'c') {
                System.out.println(f.getPath() + " " + f.getStartIndx() + " " + (f.getAllocatedBlocks()[f.getAllocatedBlocks().length - 1] + 1 - f.getStartIndx()) + " " + 'c');
            } else if (f.getAllocationAlgorithm() == 'l') {
                System.out.println(f.getPath() + " " + f.getStartIndx() + " " + f.getAllocatedBlocks()[f.getAllocatedBlocks().length - 1] + " " + 'l');
                int[] alblocks = f.getAllocatedBlocks();
                for (int i = 1; i < alblocks.length; i++) {
                    System.out.println(alblocks[i - 1] + " " + alblocks[i]);
                }
                System.out.println(alblocks[alblocks.length - 1] + " " + "nil");
            } else {
                System.out.println(f.getPath() + " " + f.getStartIndx() + " " + 'i');
                System.out.print(f.getStartIndx() + " ");
                int[] alblocks = f.getAllocatedBlocks();
                for (int i = 0; i < alblocks.length; i++) {
                    System.out.print(alblocks[i] + " ");
                }
               System.out.println();
            }
        }
        ArrayList<Directory> d = this.getSubDirectories();
        for (Directory ds : d) {
            ds.printFiles();
        }
        return false;
    }

}
