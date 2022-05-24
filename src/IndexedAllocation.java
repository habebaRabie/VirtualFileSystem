import java.util.ArrayList;


public class IndexedAllocation implements Allocation {

    public boolean searchForEmptyBlocks(ArrayList<Integer> fileBlocksNum, ArrayList<Integer> blockState) {
        boolean flag = false;
        for(int i=0; i< fileBlocksNum.size(); i++){
            if(blockState.get(i) == 0){
                flag = true;
            }else{
                return false;
            }
        }
        return flag;
    }
                                                //7 1 2 3 4
    public ArrayList<Integer> getAllocatedIndex(int index, ArrayList<Integer> fileBlocksNum  ,ArrayList<Integer> blockState) {
        if(blockState.get(index) == 0 && searchForEmptyBlocks(fileBlocksNum, blockState)){
            blockState.set(fileBlocksNum.get(index), 1);
            for(int i=0; i< fileBlocksNum.size(); i++){
                blockState.set(fileBlocksNum.get(i), 1);
            }
        }

        return blockState;
    }


    public ArrayList<Integer> deleteAllocatedIndex(int index, ArrayList<Integer> fileBlocksNum  ,ArrayList<Integer> blockState) { //0011001

        if(blockState.get(index) == 1 && !searchForEmptyBlocks(fileBlocksNum, blockState)){
            blockState.set(fileBlocksNum.get(index), 0);
            for(int i=0; i< fileBlocksNum.size(); i++){
                blockState.set(fileBlocksNum.get(i), 0);
            }
            System.out.println("File deleted successfully");
        }
        else{
            System.out.println( "File not exist");
        }
        return blockState;
    }

    @Override
    public boolean createFile(DiskStructureManger dsm, String[] path, int size) {
        return false;
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
    public ArrayList<Integer> deleteAllocatedIndex(int[] myAllocatedBlocks, ArrayList<Integer> blockState) {
        return null;
    }

    @Override
    public char getAllocationTec() {
        return 0;
    }

//    public String getAllPaths(String currentPath ,Directory d){
//        String paths="";
//        ArrayList<Files> files = d.getFiles();
//        ArrayList<Directory> subdirectories = d.getSubDirectories();
//        for(int i=0; i< files.size(); i++){
//            if(!files.get(i).isDeleted()){
//                paths+= currentPath + "/"+ files.get(i).getFileName() + " " + files.get(i).getAllocatedBlocks()[0] + "\n";
//                int [] temp = files.get(i).getAllocatedBlocks();
//                String x = "";
//                for(int j=0; j< temp.length; j++){
//                    x+= " "+ String.valueOf(temp[j]);
//                }
//                paths += x.trim() + "\n";
//            }
//        }
//        String temp="";
//
//        for(int i=0; i< subdirectories.size(); i++){
//            if(!subdirectories.get(i).isDeleted()){
//                temp = currentPath+ "/"+ subdirectories.get(i).getName();
//                paths += temp + "\n";
//                paths = getAllPaths(temp, subdirectories.get(i));
//            }
//        }
//        return paths;
//    }
}
