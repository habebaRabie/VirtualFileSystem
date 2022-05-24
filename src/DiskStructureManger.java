import java.io.*;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.Scanner;

public class DiskStructureManger {
    private File fileSystem = new File("VFS.txt");
    private Boolean fileExist = true;
    private int blocksNum = 0;
    private int blockSize = 1;
    private Scanner input = new Scanner(System.in);
    private Scanner commandName = new Scanner(System.in);
    private FileWriter fw;
    private Directory root;
    private ArrayList<Integer> blockState;

    public DiskStructureManger() throws IOException {
        blockState = new ArrayList<>();
        root = new Directory();
        if (! fileSystem.exists()) {
            fileExist = false;
        }
        if (! fileExist) {
            System.out.println("Enter the number of blocks");
            blocksNum = input.nextInt();
            fw = new FileWriter("VFS.txt");
            for (int i = 0; i < blocksNum; i++) {
                fw.write("0");
            }
            fw.write('\n');
            fw.close();
        } else {
            FileReader file = new FileReader("VFS.txt");
            BufferedReader buffer = new BufferedReader(file);
            String line = buffer.readLine(), currentDircName = "";
            String path = "";
            ArrayList<Directory> subDirec = new ArrayList<>();
            ArrayList<Files> subFiles = new ArrayList<>();

            Directory currentDirectory = root;
            for (int i = 0; i < line.length(); i++) {
                blockState.add(line.charAt(i) - '0');
            }
            int previousIndentation = - 1;
            while (true) {
                int indentation = 0;
                String tempLine = "";
                line = buffer.readLine();
                if (line.equals("EOF")) { //TODO Change this condition
                    while (currentDirectory.getParent() != null) {
                        currentDirectory = currentDirectory.getParent();
                    }
                    break;
                }
                for (int i = 0; i < line.length(); ++ i) {
                    if (line.charAt(i) != '\t') break;
                    indentation++;
                }
                line = line.trim();
                if (line.charAt(0) == '<') {
                    for (int j = 1; j < line.length() - 1; j++) {
                        tempLine += line.charAt(j);
                    }
                    line = tempLine;
                    if (previousIndentation == - 1) {
                        root.setName("root");
                        root.setDirectoryPath("root");
                        root.setParent(null);
                        currentDirectory = root;
                    } else {
                        Directory tempDirectory = new Directory();
                        if (indentation > previousIndentation) {
                            while (indentation - previousIndentation > 1) {
                                currentDirectory = currentDirectory.getParent();
                                previousIndentation++;
                            }
                            tempDirectory.setParent(currentDirectory);
                            tempDirectory.setName(line);
                            tempDirectory.setDirectoryPath(currentDirectory.getDirectoryPath() + "/" + tempDirectory.getName());
                            tempDirectory.setParent(currentDirectory);
                            currentDirectory.addDirectory(tempDirectory);
                            currentDirectory = tempDirectory;
                        } else if (indentation < previousIndentation) {
                            while (previousIndentation - indentation > 0) {
                                currentDirectory = currentDirectory.getParent();
                                previousIndentation--;
                            }
                            if (currentDirectory.getParent() != null)
                                currentDirectory = currentDirectory.getParent();
                            tempDirectory.setParent(currentDirectory);
                            tempDirectory.setName(line);
                            tempDirectory.setDirectoryPath(currentDirectory.getDirectoryPath() + "/" + tempDirectory.getName());
                            tempDirectory.setParent(currentDirectory);
                            currentDirectory.addDirectory(tempDirectory);
                            currentDirectory = tempDirectory;
                        } else {
                            tempDirectory.setName(line);
                            tempDirectory.setDirectoryPath(currentDirectory.getDirectoryPath() + "/" + tempDirectory.getName());
                            tempDirectory.setParent(currentDirectory);
                            currentDirectory.addDirectory(tempDirectory);
                            currentDirectory = tempDirectory;
                        }
                    }
                } else {
                    Files tempFile = new Files();
                    tempFile.setFileName(line);
                    tempFile.setParent(currentDirectory);
                    tempFile.setPath(currentDirectory.getDirectoryPath() + "/" + tempFile.getFileName());
                    currentDirectory.addFile(tempFile);
                }
                previousIndentation = indentation;
            }
            this.root = currentDirectory;

        }
    }

    public Boolean deleteFile(String[] path, Allocation all) {
        ArrayList<Directory> tempSub = new ArrayList<>();
        Directory current = this.getRoot();
        ArrayList<Files> f = new ArrayList<>();
        ArrayList<Integer> blockState = new ArrayList<>();
        boolean found = false;
        Files fileToDelete = new Files();
        for (int i = 1; i < path.length - 1; i++) {
            tempSub = current.getSubDirectories();
            for (int j = 0; j < tempSub.size(); j++) {
                if (tempSub.get(j).getName().equals(path[i])) {
                    current = tempSub.get(j);
                    break;
                }
                if (j + 1 >= tempSub.size()) {
                    System.out.println("Directory Not Found");
                    return false;
                }
            }
        }
        f = current.getFiles();
        for (Files file : f) {
            if (file.getFileName().equals(path[path.length - 1])) {
                found = true;
                fileToDelete = file;
                break;
            }
        }
        if (! found) {
            System.out.println("File Not Found");
            return false;
        }
        if (all.getAllocationTec() == 'c') {
            if (fileToDelete.getAllocationAlgorithm() == all.getAllocationTec()) {
                this.blockState = all.deleteAllocatedIndex(fileToDelete.getAllocatedBlocks(),this.blockState);
                Directory parent = fileToDelete.getParent();
                f = parent.getFiles();
                f.remove(f.indexOf(fileToDelete));
                parent.setFiles(f);
                fileToDelete.setParent(null);
                fileToDelete.setPath(" ");
                fileToDelete.setDeleted(true);
            } else {
                System.out.println("Wrong Allocation Technique Used to Delete");
                return false;
            }
        } else if (all.getAllocationTec() == 'l') {
            if (fileToDelete.getAllocationAlgorithm()== all.getAllocationTec()) {
                this.blockState = all.deleteAllocatedIndex(fileToDelete.getAllocatedBlocks(),this.blockState);
                Directory parent = fileToDelete.getParent();
                f = parent.getFiles();
                f.remove(f.indexOf(fileToDelete));
                parent.setFiles(f);
                fileToDelete.setParent(null);
                fileToDelete.setPath(" ");
                fileToDelete.setDeleted(true);
            } else {
                System.out.println("Wrong Allocation Technique Used to Delete");
                return false;
            }
        } else if (all.getAllocationTec() == 'i') {
            if (fileToDelete.getAllocationAlgorithm()== all.getAllocationTec()) {
                this.blockState = all.deleteAllocatedIndex(fileToDelete.getAllocatedBlocks(),this.blockState);
                Directory parent = fileToDelete.getParent();
                f = parent.getFiles();
                f.remove(f.indexOf(fileToDelete));
                parent.setFiles(f);
                fileToDelete.setParent(null);
                fileToDelete.setPath(" ");
                fileToDelete.setDeleted(true);
            } else {
                System.out.println("Wrong Allocation Technique Used to Delete");
                return false;
            }
        }
        System.out.println("File Deleted Successfully");
        return true;
    }

    public Boolean DisplayDiskStatus() {
        int spaceFilled = this.calculateSpace();
        int freeSpace = blockState.size() - spaceFilled;
        System.out.println("1- Empty Space: " + freeSpace);
        System.out.println("2- Allocated  Space: " + spaceFilled);
        System.out.print("3- Empty Blocks in the Disk: ");
        for (int i = 0; i < blockState.size(); i++) {
            if (blockState.get(i) == 0) {
                System.out.print(i + " ");
            }
        }
        System.out.println("\n4- Allocated  Blocks in the Disk: ");
        for (int i = 0; i < blockState.size(); i++) {
            if (blockState.get(i) == 1) {
                System.out.print(i + " ");
            }
        }
        return true;
    }

    public Boolean creatDirectory(String[] path) {
        ArrayList<Directory> tempSub = new ArrayList<>();
        Directory current = this.getRoot();
        boolean found = false;
        for (int i = 1; i < path.length - 1; i++) {
            tempSub = current.getSubDirectories();
            for (int j = 0; j < tempSub.size(); j++) {
                if (tempSub.get(j).getName().equals(path[i])) {
                    current = tempSub.get(j);
                    break;
                }
                if (j + 1 >= tempSub.size()) {
                    System.out.println("Directory Not Found");
                    return false;
                }
            }
        }
        tempSub = current.getSubDirectories();
        for (Directory d : tempSub) {
            if (d.getName().equals(path[path.length - 1])) {
                System.out.println("Directory Already Exist");
                return false;
            }
        }
        Directory newDirectory = new Directory();
        newDirectory.setParent(current);
        newDirectory.setName(path[path.length - 1]);
        String tempPath = "";
        for (int i = 0; i < path.length; i++) {
            tempPath += path[i];
            if (i + 1 < path.length) tempPath += "/";
        }
        newDirectory.setDirectoryPath(tempPath);
        current.addDirectory(newDirectory);
        System.out.println("Directory Created Successfully");
        return true;
    }

    public Boolean deleteDirectory(String[] path) {
        ArrayList<Directory> tempSub = new ArrayList<>();
        Directory current = this.getRoot();
        boolean found = false;
        for (int i = 1; i < path.length - 1; i++) {
            tempSub = current.getSubDirectories();
            for (int j = 0; j < tempSub.size(); j++) {
                if (tempSub.get(j).getName().equals(path[i])) {
                    current = tempSub.get(j);
                    break;
                }
                if (j + 1 >= tempSub.size()) {
                    System.out.println("Directory Not Found");
                    return false;
                }
            }
        }
        tempSub = current.getSubDirectories();
        for (Directory d : tempSub) {
            if (d.getName().equals(path[path.length - 1])) {
                found = true;
                current = d;
                break;
            }
        }
        if (! found) {
            System.out.println("Directory Not Found");
            return false;
        }
        Directory parent = current.getParent();
        ArrayList<Directory> directories = parent.getSubDirectories();
        directories.remove(directories.indexOf(current));
        current.setParent(null);
        current.setDirectoryPath(" ");
        current.setDeleted(true);
        current.setFiles(null);
        current.setSubDirectories(null);
        System.out.println("Folder Deleted Successfully");
        return true;
    }

    public Directory getRoot() {
        return this.root;
    }

    public void setRoot(Directory root) {
        this.root = root;
    }

    public Boolean getFileExist() {
        return fileExist;
    }

    public ArrayList<Integer> getBlockState() {
        return blockState;
    }

    public void setBlockState(ArrayList<Integer> bs) {
        this.blockState = bs;
    }

    public int calculateSpace() {
        int spaceCount = 0;
        for (int i = 0; i < blockState.size(); i++) {
            if (blockState.get(i) == 1) spaceCount++;
        }
        return spaceCount;
    }
}
