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

    public void addDirectory(Directory d){
        this.subDirectories.add(d);
    }
    public void addFile(Files f){
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

    public void printDirectoryStructure(int level) {
//	this method prints the directory name and its files
//    then makes recursion to loop on the subDirectories to print their structure too.
//    The level parameter can be used to print spaces before the directory name is printed to show its level in the structure
    }


}
