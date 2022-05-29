import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class User {
    String name;
    String password;
    ArrayList<String> path = new ArrayList<>();
    ArrayList<Integer> Capabilities = new ArrayList<>();
    static ArrayList<User> allusers = new ArrayList<>();


    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    //ahmed,123
    //ahmed
    public static void readUsers() throws FileNotFoundException {
        File myObj = new File("user.txt");
        Scanner myReader = new Scanner(myObj);
        String d;
        while (myReader.hasNextLine()) {
            d = myReader.nextLine();
            String[] t = d.split(",");
            User u = new User(t[0], t[1]);
            allusers.add(u);
        }
    }

    public static void writeUsers() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("user.txt"));
        String d = "";
        for (int i = 0; i < allusers.size(); i++) {
            d += allusers.get(i).getName() + "," + allusers.get(i).getPassword() + "\n";
        }
        writer.print(d);
        writer.close();
    }

    //Folder path , user name 1, Create and delete capability digits
    //root,admin,11
    //root\Folder1,ahmed,10,bora3y,11,habeba,11
    public static void readCapabilities() throws FileNotFoundException {
        File myObj = new File("capabilities.txt");
        Scanner myReader = new Scanner(myObj);
        String d;
        while (myReader.hasNextLine()) {
            d = myReader.nextLine();
            String[] t = d.split(",");
            int y = 2;
            for (int j = 0; j < allusers.size(); j++) {
                if(allusers.get(j).getName() == t[y-1]){
                    allusers.get(j).getPath().add(t[0]);
                    allusers.get(j).getCapabilities().add(Integer.parseInt(t[y]));
                }
                y += 2;
            }
        }
    }

    //allusers --> habeba, root\Folder1\Folder3
    //root\Folder1,ahmed,10,bora3y,11,
    // root\Folder1\Folder3,habeba,11,
    public static void writeCapabilities(String userCap) throws FileNotFoundException {
        String[] line = userCap.split(" ");
        String path = line[2];
        File myObj = new File("capabilities.txt");
        Scanner myReader = new Scanner(myObj);
        PrintWriter writer = new PrintWriter(new File("capabilities.txt"));
        String d = "";
        while (myReader.hasNextLine()) {
            d = myReader.nextLine();
            String[] t = d.split(",");
            if(t[0].equals(path)){
                
            }
        }

//        for (int i = 0; i < allusers.get(0).getPath().size(); i++) {
//            d += allusers.get(0).getPath().get(i);
//            for (int j = 0; j < allusers.size(); j++) {
//                String x = (allusers.get(j).getCapabilities().get(i)).toString();
//                if (x.length() == 1) {
//                    x = "0" + x;
//                }
//                d += "," + allusers.get(j).getName() + "," + x;
//            }
//            d += "\n";
//        }
//        writer.print(d);
//        writer.close();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getPath() {
        return path;
    }

    public void setPath(ArrayList<String> path) {
        this.path = path;
    }

    public ArrayList<Integer> getCapabilities() {
        return Capabilities;
    }

    public void setCapabilities(ArrayList<Integer> capabilities) {
        Capabilities = capabilities;
    }


}
