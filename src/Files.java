public class Files {
    private String filePath;
    private int[] allocatedBlocks;
    private boolean deleted;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
}
