package site.easystartup.easystartupcore.storag.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import site.easystartup.easystartupcore.storag.service.FileLocalStorageService;
import site.easystartup.easystartupcore.storag.service.StorageService;

@Configuration
public class FileConfig {
    @Bean
    @Scope("singleton")
    public StorageService storageService() {
        return new FileLocalStorageService();
    }
}
