import java.util.ArrayList;

public class linkedAllocation {

    public boolean searchForEmptyBlocks(ArrayList<Integer> intermediatePointers, ArrayList<Integer> blockState) {
        boolean flag = false;
        for(int i=0; i< intermediatePointers.size(); i++){
            if(blockState.get(i) == 0){
                flag = true;
            }else{
                return false;
            }
        }
        return flag;
    }

    public ArrayList<Integer> getAllocatedIndex(int start, int end, ArrayList<Integer> intermediatePointers , ArrayList<Integer> blockState) {
        if(blockState.get(start) == 0 && blockState.get(end) == 0 && searchForEmptyBlocks(intermediatePointers, blockState)){
            for(int i=0; i< intermediatePointers.size(); i++){
                blockState.set(intermediatePointers.get(i), 1);
            }
        }
        return blockState;
    }

    public ArrayList<Integer> deleteAllocatedIndex(int start, int end, ArrayList<Integer> intermediatePointers , ArrayList<Integer> blockState) {

        if(blockState.get(start) == 1 && blockState.get(end) == 1 && !searchForEmptyBlocks(intermediatePointers, blockState)){
            blockState.set(start,0);
            blockState.set(end,0);
            for(int i=0; i< intermediatePointers.size(); i++){
                blockState.set(intermediatePointers.get(i), 0);
            }
            System.out.println("File deleted successfully");
        }
        else{
            System.out.println( "File not exist");
        }
        return blockState;
    }
}


