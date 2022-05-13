import java.util.ArrayList;
import java.util.Scanner;

public class VirtualFileSystem {

    static int blocksNum = 0;
    static int blockSize = 1;
    static Directory root = new Directory();
    static ArrayList<Integer> blockState = new ArrayList<>();

    public static void main(String[] args) {
        int methodChoice;
        Scanner input = new Scanner(System.in);
        root.setDirectoryPath("root");

        System.out.println("Enter the number of blocks");
        blocksNum= input.nextInt();

        while(true){
            System.out.println("1 - Contiguous Allocation");
            System.out.println("2 - Indexed Allocation");
            System.out.println("3- Linked Allocation");
            methodChoice = input.nextInt();
            if(methodChoice == 1){
                //Contiguous Allocation
            }else if(methodChoice == 2){
                //Indexed Allocation
            }else if(methodChoice == 3){
                //Linked Allocation
            }else {
                System.out.println("Wrong choice!");
            }

        }

    }
}
