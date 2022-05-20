import java.util.ArrayList;
import java.util.*;

public class ContiguousAllocation {

    public boolean searchForEmptyBlocks(int fileSize, int startBlockNum, ArrayList<Integer> blockState) {
        boolean flag = false;
        for (int i = startBlockNum; i < fileSize; i++) {
            if (blockState.get(i) == 0) {
                flag = true;
            } else {
                return false;
            }
        }
        return flag;
    }


    public ArrayList<Integer> getAllocatedIndex(int fileSize, int startBlockNum, ArrayList<Integer> blockState) { //0011001
        if(searchForEmptyBlocks(fileSize, startBlockNum, blockState)){
            for (int i = startBlockNum; i < fileSize; i++) {
                blockState.set(i, 1);
            }
            System.out.println("File Created successfully");
        }
        else{
            System.out.println( "Couldn't allocate this file in this space");
        }
        return blockState;
    }

    public ArrayList<Integer> deleteAllocatedIndex(int fileSize, int startBlockNum, ArrayList<Integer> blockState) { //0011001
        if(!searchForEmptyBlocks(fileSize, startBlockNum, blockState)){
            for (int i = startBlockNum; i < fileSize; i++) {
                blockState.set(i, 0);
            }
            System.out.println("File deleted successfully");
        }
        else{
            System.out.println( "File not exist");
        }
        return blockState;
    }

}
//        Map<Integer, Integer> freeIndexes = new HashMap<Integer, Integer>(); //size, startIndex
//
//        int lastFreeIndex = -1;
//        int size = 0;
//        for (int i = 0; i < blockState.size(); i++) {
//            if(blockState.get(i) == 0){
//                size++;
//                lastFreeIndex = i;
//            }else{
//                freeIndexes.put(size, lastFreeIndex-size);
//                size= 0;
//                lastFreeIndex = -1;
//            }
//        }
//
//        int startIndex;
//
//        for(int i=0; i< blockState.size(); i++){
//            if(freeIndexes.containsKey(fileSize+i)){
//                startIndex = freeIndexes.get(fileSize+i);
//                return startIndex; //return the start index
//            }
//        }
//        return -1; //no valid space
//    }
