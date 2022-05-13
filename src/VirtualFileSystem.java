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
            System.out.println("4 - Exit");
            methodChoice = input.nextInt();
            if(methodChoice == 1){ //Best Fit allocation

                //Contiguous Allocation
                //This method keeps the free/busy list in order by size –
                // smallest to largest. In this method,
                //the operating system first searches the whole of the memory according to the size of the given job and
                // allocates it to the closest-fitting free partition in the memory

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
