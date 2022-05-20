import java.util.ArrayList;

public class linkedAllocation {

    public int[] getAllocatedIndex(int fileSize, int start, int end, ArrayList<Integer> blockState) {
        int[] array = new int[fileSize];
        int size = 0, temp;
        boolean linked = false;
        for (int i = start; i < end; i++) {
            if (blockState.get(i) == 0 && size != fileSize) {
                array[i] = 1;
                size++;
            }
            if (size == fileSize) {
                int count = 0;
                while (count < fileSize) {
                    if (array[i - count] != 1) {
                        size = 0;
                        for (int j = 0; j < fileSize; j++) {
                            array[j] = 0;
                            count++;
                        }
                        count = fileSize;
                        break;
                    } else if (count + 1 == fileSize) {
                        linked = true;
                        break;
                    }
                }
            }
            if (linked) {
                return array;
            }
        }
        return null;
    }
//    public void allocated(int fileSize, int start, int end, ArrayList<Integer>
//        int[] array = new int [fileSize];
//         array = getAllocatedIndex( fileSize, start, end, blockState);
//    }
}


