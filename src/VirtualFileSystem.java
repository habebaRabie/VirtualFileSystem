import java.io.*;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.Scanner;

public class VirtualFileSystem {

    static int blocksNum = 0;
    static int blockSize = 1;
    static boolean fileExist = true;
    static FileWriter fw;
    static Directory root = new Directory();
    static ArrayList<Integer> blockState = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int methodChoice;
        String commandIn;
        Scanner input = new Scanner(System.in);
        root.setDirectoryPath("root");
        while (true) {
            System.out.println("1 - Contiguous Allocation");
            System.out.println("2 - Indexed Allocation");
            System.out.println("3- Linked Allocation");
            System.out.println("4 - Exit");
            methodChoice = input.nextInt();
            if (methodChoice == 1) { //Best Fit allocation
                File fileSystem = new File("VFS.txt");
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
                        if (line.equals("EOF")) {
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
                    root = currentDirectory;
                    fw = new FileWriter("VFS.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    commandIn = input.nextLine();
                    String[] commandSplited = commandIn.split("\\s+");
                    if (commandSplited[0].equals("CreateFile ")) {

                    }

                    //Contiguous Allocation
                    //This method keeps the free/busy list in order by size –
                    // smallest to largest. In this method,
                    //the operating system first searches the whole of the memory according to the size of the given job and
                    // allocates it to the closest-fitting free partition in the memory
                }
            } else if (methodChoice == 2) {
                //Indexed Allocation
            } else if (methodChoice == 3) {
                //Linked Allocation
            } else if (methodChoice == 4) {
                break;
            } else {
                System.out.println("Wrong choice!");
            }

        }

    }
}

