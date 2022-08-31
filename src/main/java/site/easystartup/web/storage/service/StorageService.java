package site.easystartup.web.storage.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;
@Service
public interface StorageService {
    void init();
    void save(MultipartFile file);
    void saveImage(MultipartFile file, String name);
    Resource load(String filename);
    void deleteAll();
    Stream<Path> loadAll();
}