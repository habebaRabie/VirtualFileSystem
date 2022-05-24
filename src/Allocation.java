import java.util.ArrayList;

public interface Allocation {
    public boolean createFile(DiskStructureManger dsm, String[] path, int size);

    public boolean searchForEmptyBlocks(int fileSize, int startBlockNum, ArrayList<Integer> blockState);

    public ArrayList<Integer> getAllocatedIndex(int fileSize, int startBlockNum, ArrayList<Integer> blockState);

    public ArrayList<Integer> deleteAllocatedIndex(int[] myAllocatedBlocks, ArrayList<Integer> blockState);

    public char getAllocationTec();
}
