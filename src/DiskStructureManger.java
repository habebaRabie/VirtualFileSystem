import java.io.*;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.Scanner;

public class DiskStructureManger {
    private File fileSystem = new File("VFS.vfs");
    private Boolean fileExist = true;
    private int blocksNum = 0;
    private int blockSize = 1;
    private Scanner input = new Scanner(System.in);
    private Scanner commandName = new Scanner(System.in);
    private FileWriter fw;
    private Directory root, current = root;
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
            fw = new FileWriter("VFS.vfs");
            for (int i = 0; i < blocksNum; i++) {
                fw.write("0");
            }
            fw.write('\n');
            fw.close();
        } else {
            FileReader file = new FileReader("VFS.vfs");
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
                if (line.equals("")) { //TODO Change this condition
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
            String[] lineSplit;
            while (true) {
                line = buffer.readLine();
                if (line.equals("")) break;
                lineSplit = line.split("\\s+");
                path = lineSplit[0];
                if (lineSplit.length == 4) {
                    if (lineSplit[lineSplit.length - 1].equals("c")) {
                        Files f = root.findFile(path);
                        int start = Integer.parseInt(lineSplit[1]), end = start + Integer.parseInt(lineSplit[2]), it = start;
                        int[] allocatedBlocks = new int[end - start];
                        for (int i = 0; i < end - start; i++) {
                            this.blockState.set(it, 1);
                            allocatedBlocks[i] = it++;
                        }
                        f.setStartIndx(start);
                        f.setAllocationAlgorithm('c');
                        f.setAllocatedBlocks(allocatedBlocks);
                    } else if (lineSplit[lineSplit.length - 1].equals("l")) {
                        Files f = root.findFile(path);
                        int start = Integer.parseInt(lineSplit[1]), end = Integer.parseInt(lineSplit[2]), it = start;
                        ArrayList<Integer> all = new ArrayList<>();
                        while (true) {
                            line = buffer.readLine();
                            lineSplit = line.split("\\s+");
                            if (Integer.parseInt(lineSplit[0]) == end) break;
                            all.add(Integer.parseInt(lineSplit[0]));
                        }
                        all.add(end);
                        int[] allocatedBlocks = new int[all.size()];
                        for (int i = 0; i < all.size(); i++) {
                            allocatedBlocks[i] = all.get(i);
                            blockState.set(all.get(i), 1);
                        }
                        f.setStartIndx(start);
                        f.setAllocationAlgorithm('l');
                        f.setAllocatedBlocks(allocatedBlocks);
                    }
                } else if (lineSplit.length == 3) {
                    if (lineSplit[lineSplit.length - 1].equals("i")) {
                        Files f = root.findFile(path);
                        int start = Integer.parseInt(lineSplit[1]);
                        ArrayList<Integer> all = new ArrayList<>();
                        f.setStartIndx(start);
                        line = buffer.readLine();
                        lineSplit = line.split("\\s+");
                        blockState.set(start, 1);
                        for (int i = 1; i < lineSplit.length; ++ i) {
                            blockState.set(Integer.parseInt(lineSplit[i]), 1);
                            all.add(Integer.parseInt(lineSplit[i]));
                        }
                        int[] allocatedBlocks = new int[all.size()];
                        for (int i = 0; i < all.size(); i++) {
                            allocatedBlocks[i] = all.get(i);
                            blockState.set(all.get(i), 1);
                        }
                        f.setStartIndx(start);
                        f.setAllocationAlgorithm('i');
                        f.setAllocatedBlocks(allocatedBlocks);
                    }
                }
            }
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
                this.blockState = all.deleteAllocatedIndex(fileToDelete.getAllocatedBlocks(), this.blockState);
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
            if (fileToDelete.getAllocationAlgorithm() == all.getAllocationTec()) {
                this.blockState = all.deleteAllocatedIndex(fileToDelete.getAllocatedBlocks(), this.blockState);
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
            if (fileToDelete.getAllocationAlgorithm() == all.getAllocationTec()) {
                this.blockState = all.deleteAllocatedIndex(fileToDelete.getAllocatedBlocks(), this.blockState);
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
        System.out.println("1- Empty Space: " + freeSpace + "KB");
        System.out.println("2- Allocated  Space: " + spaceFilled + "KB");
        System.out.print("3- Empty Blocks in the Disk: ");
        for (int i = 0; i < blockState.size(); i++) {
            if (blockState.get(i) == 0) {
                System.out.print(i + " ");
            }
        }
        System.out.print("\n4- Allocated  Blocks in the Disk: ");
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

    public boolean exit() throws IOException {
        //this.fileSystem.delete();
        File fileSystem2 = new File("VFS.vfs");
        PrintStream stream = new PrintStream(fileSystem2);
        PrintStream stdout = System.out;
        System.setOut(stream);
        for (Integer state : blockState) {
            System.out.print(String.valueOf(state));
        }
        System.out.println();
        this.current = root;
        root.printDirectoryStructure();
        System.out.println("");
        root.printFiles();
        System.out.println("");
        System.setOut(stdout);

        return true;
    }
}
