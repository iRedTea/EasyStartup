package site.easystartup.easystartupcore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import site.easystartup.easystartupcore.storage.FileLocalStorageService;
import site.easystartup.easystartupcore.storage.StorageService;

@Configuration
public class FileConfig {
    @Bean
    @Scope("singleton")
    public StorageService storageService() {
        return new FileLocalStorageService();
    }
}
