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

    public static void writeCapabilities(String userCap) throws FileNotFoundException {
        String[] UserCapabil = userCap.split("\\s+");
        File myObj = new File("capabilities.txt");
        Scanner myReader = new Scanner(myObj);
        String d = "";
        String data;
        String[] data2;
        boolean flag = false;
        while (myReader.hasNextLine()) {

            data = myReader.nextLine();
            data2 = data.split("\\s");
            if(data2[0].equals(UserCapabil[2])){
                data +=" "+ UserCapabil[1]+ " " +UserCapabil[3] ;
                flag = true;
            }
            data+="\n";
            d+=data;
        }
        if(!flag){
            d+=UserCapabil[2] +" "+UserCapabil[1]+ " " +UserCapabil[3]+"\n";
        }

        PrintWriter writer = new PrintWriter(new File("capabilities.txt"));
        writer.print(d);
        writer.close();
    }

    //    static int blocksNum = 0;
//    static int blockSize = 1;
//    static boolean fileExist = true;
    static FileWriter fw;
    //    static Directory root = new Directory();
    static ArrayList<Integer> blockState = new ArrayList<>();

    public static boolean userSearch(String name) {
        for (User u : allUsers) {
            if (u.getName().equals(name)) return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        readUsers();
        int methodChoice;
        String commandIn;
        Scanner input = new Scanner(System.in);
        Scanner commandName = new Scanner(System.in);
        Scanner userInfo = new Scanner(System.in);
        Scanner createOrNot = new Scanner(System.in);
        DiskStructureManger dsm = new DiskStructureManger();
        int userNum = -1; //our user
        Allocation all;
        String[] UserInfo;
        String Info;
//        String[] s = {"root", "Folder1", "file3"};
//        dsm.getRoot().addUserTo("root/folder3/folder4","ahmed","10");
        while (true){   System.out.println("Please login to the system");
//            System.out.println("Please enter username :");
//            userName = userInfo.nextLine();
//            System.out.println("Please enter Password :");
            Info = userInfo.nextLine();
            UserInfo = Info.split("\\s+");
            boolean flag = false;
            if (UserInfo[0].equals("Login")) {
                flag = false;
                boolean userExist = false;
                for (int i = 0; i < allUsers.size(); i++) {
                    if (allUsers.get(i).getName().equals(UserInfo[1]) && allUsers.get(i).getPassword().equals(UserInfo[2])) {
                        userNum = i;
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                } else {
                    System.out.println("Wrong UserName or Password");
                }
            }
        }


        while (true) {

            System.out.println("1 - Contiguous Allocation");
            System.out.println("2 - Indexed Allocation");
            System.out.println("3 - Linked Allocation");
            System.out.println("4 - Your user information");
            System.out.println("5 - Admin Commands");
            System.out.println("6 - Exit");
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
                    Directory tempDirc = dsm.getRoot().getDirectoryParent(commandSplited[1]);
                    ArrayList<String> cap = tempDirc.getCapabilities();
                    boolean allowed = false;
                    if (UserInfo[1].equals("admin")) {
                        allowed = true;
                    } else if (cap.size() > 0 && tempDirc.getUsers().contains(UserInfo[1])) {
                        if (cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("10") || cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("11")) {
                            allowed = true;
                        }
                    }
                    if (allowed) {
                        all.createFile(dsm, directory, Integer.parseInt(commandSplited[2]));
                    } else {
                        System.out.println("You Don't Have Permission");
                    }
                } else if (commandSplited[0].equals("DeleteFile")) { //
                    Directory tempDirc = dsm.getRoot().getDirectoryParent(commandSplited[1]);
                    ArrayList<String> cap = tempDirc.getCapabilities();
                    directory = commandSplited[1].split("/");
                    boolean allowed = false;
                    if (UserInfo[1].equals("admin")) {
                        allowed = true;
                    } else if (cap.size() > 0) {
                        if (cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("01") || cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("11")) {
                            allowed = true;
                        }
                    }
                    if (allowed) {
                        dsm.deleteFile(directory, all);
                    } else {
                        System.out.println("You Don't Have Permission");
                    }
                } else if (commandSplited[0].equals("CreateFolder")) { //
                    Directory tempDirc = dsm.getRoot().getDirectoryParent(commandSplited[1]);
                    ArrayList<String> cap = tempDirc.getCapabilities();
                    boolean allowed = false;
                    if (UserInfo[1].equals("admin")) {
                        allowed = true;
                    } else if (cap.size() > 0 && tempDirc.getUsers().contains(UserInfo[1])) {
                        if (cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("10") || cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("11")) {
                            allowed = true;
                        }
                    }
                    if (allowed) {
                        directory = commandSplited[1].split("/");
                        dsm.creatDirectory(directory);
                    } else {
                        System.out.println("You Don't Have Permission");
                    }
                } else if (commandSplited[0].equals("DeleteFolder")) { //
                    Directory tempDirc = dsm.getRoot().getDirectoryParent(commandSplited[1]);
                    ArrayList<String> cap = tempDirc.getCapabilities();
                    boolean allowed = false;
                    directory = commandSplited[1].split("/");
                    if (UserInfo[1].equals("admin")) {
                        allowed = true;
                    } else if (cap.size() > 0 && tempDirc.getUsers().contains(UserInfo[1])) {
                        if (cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("01") || cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("11")) {
                            allowed = true;
                        }
                    }
                    if (allowed) {
                        dsm.deleteDirectory(directory);
                    } else {
                        System.out.println("You Don't Have Permission");
                    }
                } else if (commandSplited[0].equals("DisplayDiskStructure")) {
                    dsm.getRoot().printDirectoryStructure();

                } else if (commandSplited[0].equals("DisplayDiskStatus")) {
                    dsm.DisplayDiskStatus();
                } else if (commandSplited[0].equals("Grant")) {
                    if (UserInfo[1].equals("admin")) {
                        if (userSearch(commandSplited[1])) {
                            if (dsm.getRoot().searchPath(commandSplited[2]))
                                dsm.getRoot().addUserTo(commandSplited[2], commandSplited[1], commandSplited[3]);
                            else System.out.println("Path Not Found");
                        } else {
                            System.out.println("User Not Found");
                        }
                    } else System.out.println("You Are Not Authorized");
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
                    directory = commandSplited[1].split("/");
                    Directory tempDirc = dsm.getRoot().getDirectoryParent(commandSplited[1]);
                    ArrayList<String> cap = tempDirc.getCapabilities();
                    boolean allowed = false;
                    if (UserInfo[1].equals("admin")) {
                        allowed = true;
                    } else if (cap.size() > 0 && tempDirc.getUsers().contains(UserInfo[1])) {
                        if (cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("10") || cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("11")) {
                            allowed = true;
                        }
                    }
                    if (allowed) {
                        all.createFile(dsm, directory, Integer.parseInt(commandSplited[2]));
                    } else {
                        System.out.println("You Don't Have Permission");
                    }
                } else if (commandSplited[0].equals("DeleteFile")) { //
                    directory = commandSplited[1].split("/");
                    Directory tempDirc = dsm.getRoot().getDirectoryParent(commandSplited[1]);
                    ArrayList<String> cap = tempDirc.getCapabilities();
                    boolean allowed = false;
                    if (UserInfo[1].equals("admin")) {
                        allowed = true;
                    } else if (cap.size() > 0) {
                        if (cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("01") || cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("11")) {
                            allowed = true;
                        }
                    }
                    if (allowed) {
                        dsm.deleteFile(directory, all);
                    } else {
                        System.out.println("You Don't Have Permission");
                    }
                } else if (commandSplited[0].equals("CreateFolder")) { //
                    Directory tempDirc = dsm.getRoot().getDirectoryParent(commandSplited[1]);
                    ArrayList<String> cap = tempDirc.getCapabilities();
                    boolean allowed = false;
                    if (UserInfo[1].equals("admin")) {
                        allowed = true;
                    } else if (cap.size() > 0 && tempDirc.getUsers().contains(UserInfo[1])) {
                        if (cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("10") || cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("11")) {
                            allowed = true;
                        }
                    }
                    if (allowed) {
                        directory = commandSplited[1].split("/");
                        dsm.creatDirectory(directory);
                    } else {
                        System.out.println("You Don't Have Permission");
                    }
                } else if (commandSplited[0].equals("DeleteFolder")) { //
                    Directory tempDirc = dsm.getRoot().getDirectoryParent(commandSplited[1]);
                    ArrayList<String> cap = tempDirc.getCapabilities();
                    boolean allowed = false;
                    directory = commandSplited[1].split("/");
                    if (UserInfo[1].equals("admin")) {
                        allowed = true;
                    } else if (cap.size() > 0 && tempDirc.getUsers().contains(UserInfo[1])) {
                        if (cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("01") || cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("11")) {
                            allowed = true;
                        }
                    }
                    if (allowed) {
                        dsm.deleteDirectory(directory);
                    } else {
                        System.out.println("You Don't Have Permission");
                    }
                } else if (commandSplited[0].equals("DisplayDiskStructure")) {
                    dsm.getRoot().printDirectoryStructure();

                } else if (commandSplited[0].equals("DisplayDiskStatus")) {
                    dsm.DisplayDiskStatus();
                } else if (commandSplited[0].equals("Grant")) {
                    if (UserInfo[1].equals("admin")) {
                        if (userSearch(commandSplited[1])) {
                            if (dsm.getRoot().searchPath(commandSplited[2]))
                                dsm.getRoot().addUserTo(commandSplited[2], commandSplited[1], commandSplited[3]);
                            else System.out.println("Path Not Found");
                        } else {
                            System.out.println("User Not Found");
                        }
                    } else System.out.println("You Are Not Authorized");
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
                    Directory tempDirc = dsm.getRoot().getDirectoryParent(commandSplited[1]);
                    ArrayList<String> cap = tempDirc.getCapabilities();
                    boolean allowed = false;
                    if (UserInfo[1].equals("admin")) {
                        allowed = true;
                    } else if (cap.size() > 0 && tempDirc.getUsers().contains(UserInfo[1])) {
                        if (cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("10") || cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("11")) {
                            allowed = true;
                        }
                    }
                    if (allowed) {
                        all.createFile(dsm, directory, Integer.parseInt(commandSplited[2]));
                    } else {
                        System.out.println("You Don't Have Permission");
                    }
                } else if (commandSplited[0].equals("DeleteFile")) { //
                    Directory tempDirc = dsm.getRoot().getDirectoryParent(commandSplited[1]);
                    ArrayList<String> cap = tempDirc.getCapabilities();
                    directory = commandSplited[1].split("/");
                    boolean allowed = false;
                    if (UserInfo[1].equals("admin")) {
                        allowed = true;
                    } else if (cap.size() > 0) {
                        if (cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("01") || cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("11")) {
                            allowed = true;
                        }
                    }
                    if (allowed) {
                        dsm.deleteFile(directory, all);
                    } else {
                        System.out.println("You Don't Have Permission");
                    }
                } else if (commandSplited[0].equals("CreateFolder")) { //
                    Directory tempDirc = dsm.getRoot().getDirectoryParent(commandSplited[1]);
                    ArrayList<String> cap = tempDirc.getCapabilities();
                    boolean allowed = false;
                    if (UserInfo[1].equals("admin")) {
                        allowed = true;
                    } else if (cap.size() > 0 && tempDirc.getUsers().contains(UserInfo[1])) {
                        if (cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("10") || cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("11")) {
                            allowed = true;
                        }
                    }
                    if (allowed) {
                        directory = commandSplited[1].split("/");
                        dsm.creatDirectory(directory);
                    } else {
                        System.out.println("You Don't Have Permission");
                    }
                } else if (commandSplited[0].equals("DeleteFolder")) { //
                    Directory tempDirc = dsm.getRoot().getDirectoryParent(commandSplited[1]);
                    ArrayList<String> cap = tempDirc.getCapabilities();
                    boolean allowed = false;
                    directory = commandSplited[1].split("/");
                    if (UserInfo[1].equals("admin")) {
                        allowed = true;
                    } else if (cap.size() > 0 && tempDirc.getUsers().contains(UserInfo[1])) {
                        if (cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("01") || cap.get(tempDirc.getUsers().indexOf(UserInfo[1])).equals("11")) {
                            allowed = true;
                        }
                    }
                    if (allowed) {
                        dsm.deleteDirectory(directory);
                    } else {
                        System.out.println("You Don't Have Permission");
                    }
                } else if (commandSplited[0].equals("DisplayDiskStructure")) {
                    dsm.getRoot().printDirectoryStructure();

                } else if (commandSplited[0].equals("DisplayDiskStatus")) {
                    dsm.DisplayDiskStatus();
                } else if (commandSplited[0].equals("Grant")) {
                    if (UserInfo[1].equals("admin")) {
                        if (userSearch(commandSplited[1])) {
                            if (dsm.getRoot().searchPath(commandSplited[2]))
                                dsm.getRoot().addUserTo(commandSplited[2], commandSplited[1], commandSplited[3]);
                            else System.out.println("Path Not Found");
                        } else {
                            System.out.println("User Not Found");
                        }
                    } else System.out.println("You Are Not Authorized");
                } else {
                    System.out.println("Wrong command");
                }
            } else if (methodChoice == 4) {
                System.out.println("Username " + allUsers.get(userNum).getName());
            }




            else if (methodChoice == 5) {
                boolean userExist = false;

                if (allUsers.get(userNum).getName().equals("admin")) {
                    System.out.println("You want to create new user(y/n)");
                    if (createOrNot.nextLine().equals("y")) {
                        Info = userInfo.nextLine();
                        UserInfo = Info.split("\\s+");
                        if (UserInfo[0].equals("CUser")) {
                            for (int i = 0; i < allUsers.size(); i++) {
                                if (allUsers.get(i).getName().equals(UserInfo[1])) {
                                    System.out.println("Username already exist!");
                                    userExist = true;
                                    break;
                                }
                            }
                            if (!userExist) {
                                User user = new User(UserInfo[1], UserInfo[2]);
                                allUsers.add(user);
                                writeUsers();
                                System.out.println("Please enter the user access");
                                Info = userInfo.nextLine();
                                String[] UserCapabil = Info.split("\\s+");
                                if (UserCapabil[0].equals("Grant")) {
                                    //TODO
                                    writeCapabilities(Info);
                                }

                            }
                        }else {
                            System.out.println("Wrong command");
                        }
                    } else {
                        System.out.println("You can't create a user");
                    }
                } else {
                    System.out.println("Sorry You are not admin so you can not do admin commands");
                }
            }
            else if (methodChoice == 6) {
                dsm.exit();
                break;
            } else {
                System.out.println("Wrong choice!");
            }

        }

    }
}