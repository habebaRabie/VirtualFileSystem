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

    public Boolean printDirectoryStructure() {
        System.out.println("<"+this.name+">");
        if(this.subDirectories.size() == 0){
            return true;
        }else{
            ArrayList <Files> f = this.getFiles();
            for (Files file:f){
                String[] splited =file.getPath().split("/");
                for(int i = 0 ; i < splited.length-1 ;++i) System.out.print("\t");
                System.out.println(file.getFileName());
            }
            ArrayList <Directory > d = this.getSubDirectories();
            for(Directory dir:d){
                String[] splited =dir.getDirectoryPath().split("/");
                for(int i = 0 ; i < splited.length-1 ;++i) System.out.print("\t");
                dir.printDirectoryStructure();
            }
        }
        return false;
    }


}
