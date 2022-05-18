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


        while(true){
            System.out.println("1 - Contiguous Allocation");
            System.out.println("2 - Indexed Allocation");
            System.out.println("3- Linked Allocation");
            System.out.println("4 - Exit");
            methodChoice = input.nextInt();
            if(methodChoice == 1){ //Best Fit allocation
                File fileSystem = new File("VFS.txt");
                if(!fileSystem.exists()){
                    fileExist = false;
                }
                if(!fileExist){
                    System.out.println("Enter the number of blocks");
                    blocksNum= input.nextInt();
                    fw = new FileWriter("VFS.txt");
                    for(int i = 0 ; i < blocksNum ; i++){
                        fw.write("0");
                    }
                    fw.write('\n');
                    fw.close();
                }
                else {
                    FileReader file = new FileReader("VFS.txt");
                    BufferedReader buffer = new BufferedReader(file);
                    String line = buffer.readLine(), currentDircName = "";
                    String path = "";
                    ArrayList<Directory> subDirec = new ArrayList<>();
                    ArrayList<Files> subFiles = new ArrayList<>();

                    Directory currentDirectory = new Directory();
                    for (int i = 0; i < line.length(); i++) {
                        blockState.add(line.charAt(i) - '0');
                    }
                    while (true) {
                        line = buffer.readLine();
                        if (line == "\n") {
                            break;
                        }
                        String[] lineTree = line.split("\\s+");
                        for (int i = 0; i < lineTree[0].length(); i++) {
                            if (i > 0 || i < lineTree[0].length() - 1) currentDircName += lineTree[0].charAt(i);
                        }
                        if (currentDircName.equals("root")) {
                            currentDirectory = root;
                            path = "root";
                        } else {
                            path += "/"+currentDircName;
                            //TODO search function for directory by path
                        }
                        for (int i = 1; i < lineTree.length - 1; i++) {
                            String tempDircName = "";
                            if (lineTree[i].charAt(0) == '<') {
                                for (int j = 0; j < lineTree[i].length(); j++) {
                                    if (j > 0 || j < lineTree[0].length() - 1) tempDircName += lineTree[i].charAt(j);
                                }
                                Directory tempDirec = new Directory();
                                tempDirec.setDirectoryPath(path + "/" + tempDircName);
                                subDirec.add(tempDirec);
                            } else {
                                Files tempFile = new Files();
                                tempFile.setFilePath(path + "/" + lineTree[i]);
                                subFiles.add(tempFile);
                            }
                        }
                        currentDirectory.setSubDirectories(subDirec);
                        currentDirectory.setFiles(subFiles);
                    }
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
            }else if(methodChoice == 2){
                //Indexed Allocation
            }else if(methodChoice == 3){
                //Linked Allocation
            }else if(methodChoice == 4){
                break;
            }else {
                System.out.println("Wrong choice!");
            }

        }

    }
}

