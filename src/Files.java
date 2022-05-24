public class Files {
    private String fileName;
    private Directory parent;
    private String path;
    private int[] allocatedBlocks;
    private boolean deleted;
    private char allocationAlgorithm;
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String filePath) {
        this.fileName = filePath;
    }

    public void setParent(Directory dic) {
        this.parent = dic;
    }

    public Directory getParent() {
        return this.parent;
    }
    public void setAllocationAlgorithm(char c){
        this.allocationAlgorithm = c;
    }
    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int[] getAllocatedBlocks() {
        return allocatedBlocks;
    }

    public void setAllocatedBlocks(int[] allocatedBlocks) {
        this.allocatedBlocks = allocatedBlocks;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public char getAllocationAlgorithm(){
        return allocationAlgorithm;
    }
}
