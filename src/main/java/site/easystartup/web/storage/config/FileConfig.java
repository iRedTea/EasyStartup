package site.easystartup.web.storage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import site.easystartup.web.storage.service.FileLocalStorageService;
import site.easystartup.web.storage.service.StorageService;

@Configuration
public class FileConfig {
    @Bean
    @Scope("singleton")
    public StorageService storageService() {
        return new FileLocalStorageService();
    }
}
