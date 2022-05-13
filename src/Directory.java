import java.util.ArrayList;

public class Directory {
    private String directoryPath;
    private ArrayList <File> files = new ArrayList<>();
    private ArrayList<Directory> subDirectories = new ArrayList<>();
    private boolean deleted;

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
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
