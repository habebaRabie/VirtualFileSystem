import org.w3c.dom.Node;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;

public class linkedAllocation implements Allocation{
    char allocationName = 'l';


    public ArrayList<Integer> getAllocatedIndex(ArrayList<Integer> intermediatePointers , ArrayList<Integer> blockState) {
        for(int i=0; i< intermediatePointers.size(); i++){
            blockState.set(intermediatePointers.get(i), 1);
        }
        return blockState;
    }

    public ArrayList<Integer> deleteAllocatedIndex(int [] myAllocatedBlocks , ArrayList<Integer> blockState) {

        for (int i = 0; i < myAllocatedBlocks.length; i++) {
            blockState.set(myAllocatedBlocks[i], 0);
        }
        System.out.println("File deleted successfully");
        return blockState;
    }

    @Override
    public boolean createFile(DiskStructureManger dsm, String[] path, int size) {
        ArrayList<Directory> sub = new ArrayList<>();
        Directory current= dsm.getRoot();
        ArrayList <Files> file = new ArrayList<>();
        ArrayList<Integer> blockState = dsm.getBlockState();
        for(int i=1 ; i<path.length-1;i++){
            sub = current.getSubDirectories();
            for(int j=0 ; j< sub.size(); j++){
                if(sub.get(j).getName().equals(path[i])){
                    current = sub.get(i);
                    break;
                }
                if(j+1>=sub.size()){
                    System.out.println("Directory Not Found");
                    return false;
                }
            }
        }
        file = current.getFiles();
        for(Files f: file){
            if(f.getFileName().equals(path[path.length-1])){
                System.out.println("File Already Exists");
                return false;
            }
        }
        int numberOfBlocks =0 , start = 0, end = 0;
        ArrayList<Integer> pointers = new ArrayList<>();
        boolean foundBlocks = false;

        for(int i=0; i< blockState.size(); i++){
            if(blockState.get(i) == 0){ //empty
                pointers.add(i);
                numberOfBlocks++;
            }
            if(numberOfBlocks == size){
                foundBlocks = true;
                break;
            }
        }
        if(!foundBlocks){
            System.out.println("No Linked Blocks Found To Allocate");
            return false;
        }
        Files newFile = new Files();
        newFile.setFileName(path[path.length-1]);
        newFile.setParent(current);
        String newFilePath="";
        for(int i = 0; i< path.length;i++){
            newFilePath +=path[i];
            if(i+1 < path.length) newFilePath+= "/";
        }
        newFile.setPath(newFilePath);

        blockState = getAllocatedIndex(pointers,blockState);
        int[] allocatedBlocks = new int[size];
        int i  = 0;
        for (Integer P : pointers) {
            blockState.set(P,1);
            allocatedBlocks[i++] = P;
        }
        newFile.setAllocatedBlocks(allocatedBlocks);
        newFile.setAllocationAlgorithm('l');
        current.addFile(newFile);
        dsm.setBlockState(blockState);
        return true;
    }

    @Override
    public boolean searchForEmptyBlocks(int fileSize, int startBlockNum, ArrayList<Integer> blockState) {
        return false;
    }

    @Override
    public ArrayList<Integer> getAllocatedIndex(int fileSize, int startBlockNum, ArrayList<Integer> blockState) {
        return null;
    }

    @Override
    public char getAllocationTec() {
        return allocationName;
    }
}


