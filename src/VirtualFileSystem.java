import java.io.*;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class VirtualFileSystem {
    static ArrayList<User> allUsers = new ArrayList<>();

    public static void readUsers() throws FileNotFoundException {
        File myObj = new File("user.txt");
        Scanner myReader = new Scanner(myObj);
        String d;
        while (myReader.hasNextLine()) {
            d = myReader.nextLine();
            String[] t = d.split(",");
            User u = new User(t[0], t[1]);
            allUsers.add(u);
        }
    }

    public static void writeUsers() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("user.txt"));
        String d = "";
        for (int i = 0; i < allUsers.size(); i++) {
            d += allUsers.get(i).getName() + "," + allUsers.get(i).getPassword() + "\n";
        }
        writer.print(d);
        writer.close();
    }

    //    static int blocksNum = 0;
//    static int blockSize = 1;
//    static boolean fileExist = true;
    static FileWriter fw;
    //    static Directory root = new Directory();
    static ArrayList<Integer> blockState = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        readUsers();
        int methodChoice;
        String commandIn;
        Scanner input = new Scanner(System.in);
        Scanner commandName = new Scanner(System.in);
        Scanner userInfo = new Scanner(System.in);
        Scanner createOrNot =  new Scanner(System.in);
        DiskStructureManger dsm = new DiskStructureManger();
        int userNum = -1; //our user
        Allocation all;
        String[] s = {"root", "Folder1", "file3"};

        System.out.println("Please login to the system");
        String Info;
//            System.out.println("Please enter username :");
//            userName = userInfo.nextLine();
//            System.out.println("Please enter Password :");
        Info = userInfo.nextLine();
        String[] UserInfo = Info.split("\\s+");
        while (true) {
            if (UserInfo[0].equals("Login")){
                boolean flag = false;
                boolean userExist = false;
                for(int i=0; i<allUsers.size(); i++){
                    if(allUsers.get(i).getName().equals(UserInfo[1]) &&allUsers.get(i).getPassword().equals(UserInfo[2])){
                        userNum = i;
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    if(allUsers.get(userNum).getName().equals("admin")){
                        System.out.println("You want to create new user(y/n)");
                        if(createOrNot.nextLine().equals("y")){
                            Info = userInfo.nextLine();
                            UserInfo = Info.split("\\s+");
                            if (UserInfo[0].equals("CUser")){
                                for(int i=0; i<allUsers.size(); i++){
                                    if(allUsers.get(i).getName().equals(UserInfo[1])){
                                        System.out.println("Username already exist!");
                                        userExist = true;
                                        break;
                                    }
                                }
                                if(!userExist){
                                    User user = new User(UserInfo[1], UserInfo[2]);
                                    allUsers.add(user);
                                    writeUsers();
                                    System.out.println("Please enter the user access");
                                    Info = userInfo.nextLine();
                                    UserInfo = Info.split("\\s+");
                                    if (UserInfo[0].equals("Grant")){
                                        //TODO
                                    }

                                }else{
                                    break;
                                }
                            }

                        }else {
                            System.out.println("You can't create a user");
                        }
                    }
                }else{
                    System.out.println("Wrong UserName or Password");
                    break;
                }
            }else{
                System.out.println("Wrong command");
                break;
            }
            System.out.println("1 - Contiguous Allocation");
            System.out.println("2 - Indexed Allocation");
            System.out.println("3 - Linked Allocation");
            System.out.println("4 - Your user information");
            System.out.println("5 - Exit");
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
                } else {
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
                } else {
                    System.out.println("Wrong command");
                }
            } else if (methodChoice == 4) {
                System.out.println("Username "+ allUsers.get(userNum).getName());
            }else if(methodChoice == 5){
                dsm.exit();
                break;
            }else {
                System.out.println("Wrong choice!");
            }

        }

    }
}

