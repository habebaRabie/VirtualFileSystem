import java.util.ArrayList;


public class IndexedAllocation {

    public int[] allocateBlocks(int fileSize, ArrayList<Integer> stateOfBlocks){

        int [] arr = new int[fileSize];
        int fileCheck = 0;
        for(int i=0; i< stateOfBlocks.size(); i++){
            if(stateOfBlocks.get(i) == 0 && fileCheck<=fileSize) {
                arr[i] = 1; //save index
                fileCheck++;
            }
        }
        return arr;
    }

    public String getAllPaths(String currentPath ,Directory d){
        String paths="";
        ArrayList<Files> files = d.getFiles();
        ArrayList<Directory> subdirectories = d.getSubDirectories();
        for(int i=0; i< files.size(); i++){
            if(!files.get(i).isDeleted()){
                paths+= currentPath + "/"+ files.get(i).getFileName() + " " + files.get(i).getAllocatedBlocks()[0] + "\n";
                int [] temp = files.get(i).getAllocatedBlocks();
                String x = "";
                for(int j=0; j< temp.length; j++){
                    x+= " "+ String.valueOf(temp[j]);
                }
                paths += x.trim() + "\n";
            }
        }
        String temp="";

        for(int i=0; i< subdirectories.size(); i++){
            if(!subdirectories.get(i).isDeleted()){
                temp = currentPath+ "/"+ subdirectories.get(i).getName();
                paths += temp + "\n";
                paths = getAllPaths(temp, subdirectories.get(i));
            }
        }
        return paths;
    }
}
