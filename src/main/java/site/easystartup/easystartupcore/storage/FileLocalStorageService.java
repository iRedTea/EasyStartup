package site.easystartup.easystartupcore.storage;

import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileLocalStorageService implements StorageService {
    private final Path root = Paths.get("uploads");

    public FileLocalStorageService() {
        init();
    }

    @Override
    public void init() {
        try {
            if(!root.toFile().exists())
                Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }
    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            System.out.println("Could not store the file. Error: " + e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    public void saveImage(MultipartFile file, String name) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(name + ".jpg"));
        } catch (Exception e) {
            Files.delete(this.root.resolve(name + ".jpg"));
            Files.copy(file.getInputStream(), this.root.resolve(name + ".jpg"));
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
