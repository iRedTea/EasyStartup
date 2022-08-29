package site.easystartup.web.storage.service;

public class StorageFileNotFoundException extends RuntimeException {
    public StorageFileNotFoundException(String filename) {
        super("Could not find file with name " + filename);
    }
}
