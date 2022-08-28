package site.easystartup.easystartupcore.storage;

public class StorageFileNotFoundException extends RuntimeException {
    public StorageFileNotFoundException(String filename) {
        super("Could not find file with name " + filename);
    }
}
