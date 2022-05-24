import java.util.ArrayList;
import java.util.*;

public class ContiguousAllocation implements Allocation {
    char allocationName = 'c';

    public boolean createFile(DiskStructureManger dsm, String[] path, int size) {
        ArrayList<Directory> tempSub = new ArrayList<>();
        Directory current = dsm.getRoot();
        ArrayList<Files> f = new ArrayList<>();
        ArrayList<Integer> blockState = new ArrayList<>();
        // "root","Folder1","file3"
        for (int i = 1; i < path.length - 1; i++) {
            tempSub = current.getSubDirectories();
            for (int j = 0; j < tempSub.size(); j++) {
                if (tempSub.get(j).getName().equals(path[i])) {
                    current = tempSub.get(j);
                    break;
                }
                if (j + 1 >= tempSub.size()) {
                    System.out.println("Directory Not Found");
                    return false;
                }
            }
        }
        f = current.getFiles();
        for (Files file : f) {
            if (file.getFileName().equals(path[path.length - 1])) {
                System.out.println("File Already Exists");
                return false;
            }
        }
        blockState = dsm.getBlockState();
        int blockCount = 0, start = 0;
        boolean foundBlocks = false;
        int spaceCount = dsm.calculateSpace();
        if (blockState.size() - spaceCount < size) {
            System.out.println("Not Enough Space");
            return false;
        }
        for (int i = 0; i < blockState.size(); ++ i) {
            if (blockState.get(i) == 0) blockCount++;
            if (blockCount == size) {
                start = (i + 1) - size;
                foundBlocks = true;
            } else if (blockState.get(i) == 1) blockCount = 0;
        }
        if (! foundBlocks) {
            System.out.println("No Contiguous Blocks Found To Allocate");
            return false;
        }
        Files newFile = new Files();
        newFile.setFileName(path[path.length - 1]);
        newFile.setParent(current);
        String tempPath = "";
        for (int i = 0; i < path.length; i++) {
            tempPath += path[i];
            if (i + 1 < path.length) tempPath += "/";
        }
        newFile.setPath(tempPath);
        int[] allocatedBlocks = new int[size];
        for (int i = start; i < blockCount; i++) {
            blockState.set(i, 1);
            allocatedBlocks[i] = i;
        }
        newFile.setAllocatedBlocks(allocatedBlocks);
        newFile.setAllocationAlgorithm('c');
        dsm.setBlockState(blockState);
        return true;
    }

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


    public ArrayList<Integer> getAllocatedIndex(int fileSize, int startBlockNum, ArrayList<Integer> blockState) {

        if (searchForEmptyBlocks(fileSize, startBlockNum, blockState)) {
            for (int i = startBlockNum; i < fileSize; i++) {
                blockState.set(i, 1);
            }
            System.out.println("File Created successfully");
        } else {
            System.out.println("Couldn't allocate this file in this space");
        }
        return blockState;
    }

    public ArrayList<Integer> deleteAllocatedIndex(int[] myAllocatedBlocks, ArrayList<Integer> blockState) { //0011001
        for (int i = 0; i < myAllocatedBlocks.length; i++) {
            blockState.set(myAllocatedBlocks[i], 0);
        }
        System.out.println("File deleted successfully");
        return blockState;
    }

    public char getAllocationTec() {
        return allocationName;
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
