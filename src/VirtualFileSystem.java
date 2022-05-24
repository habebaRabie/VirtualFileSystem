import java.io.*;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class VirtualFileSystem {

    //    static int blocksNum = 0;
//    static int blockSize = 1;
//    static boolean fileExist = true;
    static FileWriter fw;
    //    static Directory root = new Directory();
    static ArrayList<Integer> blockState = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int methodChoice;
        String commandIn;
        Scanner input = new Scanner(System.in);
        Scanner commandName = new Scanner(System.in);
        DiskStructureManger dsm = new DiskStructureManger();
        Allocation all;
        String[] s = {"root", "Folder1", "file3"};
        while (true) {
            System.out.println("1 - Contiguous Allocation");
            System.out.println("2 - Indexed Allocation");
            System.out.println("3 - Linked Allocation");
            System.out.println("4 - Exit");
            methodChoice = input.nextInt();

            if (methodChoice == 1) { //Best Fit allocation
                all = new ContiguousAllocation();
                fw = new FileWriter("VFS.vfs", true);
                BufferedWriter bw = new BufferedWriter(fw);
                commandIn = commandName.nextLine();
                String[] commandSplited = commandIn.split("\\s+");
                String[] directory;
                if (commandSplited[0].equals("CreateFile")) {
                    directory = commandSplited[1].split("/");
                    all.createFile(dsm, directory, Integer.parseInt(commandSplited[2]));
                } else if (commandSplited[0].equals("DeleteFile")) { //
                    directory = commandSplited[1].split("/");
                    dsm.deleteFile(directory, all);
                } else if (commandSplited[0].equals("CreateFolder")) { //
                    directory = commandSplited[1].split("/");
                    dsm.creatDirectory(directory);
                } else if (commandSplited[0].equals("DeleteFolder")) { //
                    directory = commandSplited[1].split("/");
                    dsm.deleteDirectory(directory);
                } else if (commandSplited[0].equals("DisplayDiskStructure")) {
                    dsm.getRoot().printDirectoryStructure();

                } else if (commandSplited[0].equals("DisplayDiskStatus")) {
                    dsm.DisplayDiskStatus();
                }
                //Contiguous Allocation
                //This method keeps the free/busy list in order by size –
                // smallest to largest. In this method,
                //the operating system first searches the whole of the memory according to the size of the given job and
                // allocates it to the closest-fitting free partition in the memory

            } else if (methodChoice == 2) {
                //Indexed Allocation
                all = new IndexedAllocation();
                fw = new FileWriter("VFS.vfs", true);
                BufferedWriter bw = new BufferedWriter(fw);
                commandIn = commandName.nextLine();
                String[] commandSplited = commandIn.split("\\s+");
                String[] directory;
                if (commandSplited[0].equals("CreateFile")) {
                    directory = commandSplited[1].split("/");
                    all.createFile(dsm, directory, Integer.parseInt(commandSplited[2]));
                } else if (commandSplited[0].equals("DeleteFile")) { //
                    directory = commandSplited[1].split("/");
                    dsm.deleteFile(directory, all);
                } else if (commandSplited[0].equals("CreateFolder")) { //
                    directory = commandSplited[1].split("/");
                    dsm.creatDirectory(directory);
                } else if (commandSplited[0].equals("DeleteFolder")) { //
                    directory = commandSplited[1].split("/");
                    dsm.deleteDirectory(directory);
                } else if (commandSplited[0].equals("DisplayDiskStructure")) {
                    dsm.getRoot().printDirectoryStructure();

                } else if (commandSplited[0].equals("DisplayDiskStatus")) {
                    dsm.DisplayDiskStatus();
                }else {
                    System.out.println("Wrong comand");
                }
            } else if (methodChoice == 3) {
                //Linked Allocation
                all = new linkedAllocation();
                fw = new FileWriter("VFS.vfs", true);
                BufferedWriter bw = new BufferedWriter(fw);
                commandIn = commandName.nextLine();
                String[] commandSplited = commandIn.split("\\s+");
                String[] directory;
                if (commandSplited[0].equals("CreateFile")) {
                    directory = commandSplited[1].split("/");
                    all.createFile(dsm, directory, Integer.parseInt(commandSplited[2]));
                } else if (commandSplited[0].equals("DeleteFile")) { //
                    directory = commandSplited[1].split("/");
                    dsm.deleteFile(directory, all);
                } else if (commandSplited[0].equals("CreateFolder")) { //
                    directory = commandSplited[1].split("/");
                    dsm.creatDirectory(directory);
                } else if (commandSplited[0].equals("DeleteFolder")) { //
                    directory = commandSplited[1].split("/");
                    dsm.deleteDirectory(directory);
                } else if (commandSplited[0].equals("DisplayDiskStructure")) {
                    dsm.getRoot().printDirectoryStructure();

                } else if (commandSplited[0].equals("DisplayDiskStatus")) {
                    dsm.DisplayDiskStatus();
                }else {
                    System.out.println("Wrong comand");
                }
            } else if (methodChoice == 4) {
                dsm.exit();
                break;
            } else {
                System.out.println("Wrong choice!");
            }

        }


    }
}

