package site.easystartup.web;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import site.easystartup.web.storage.domain.exception.StorageFileNotFoundException;
import site.easystartup.web.storage.service.StorageService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class FileUploadTests {

    @BeforeAll
    public static void onStart() throws Exception {
        Files.createFile(Path.of("uploads", "first.txt"));
        Files.createFile(Path.of("uploads", "second.txt"));
    }


    @Autowired
    private MockMvc mvc;

    @MockBean
    private StorageService storageService;

    @Test
    public void shouldListAllFiles() {
        given(this.storageService.loadAll())
                .willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));
    }

    @Test
    public void shouldSaveUploadedFile() {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        storageService.save(file);
        then(this.storageService).should().save(file);


    }

    @Test
    public void should404WhenMissingFile() throws Exception {
        given(this.storageService.load("test.txt"))
                .willThrow(StorageFileNotFoundException.class);

        this.mvc.perform(get("/files/test.txt")).andExpect(status().isNotFound());
    }

    @AfterAll
    public static void onEnd() throws Exception {
        Files.deleteIfExists(Path.of("uploads", "first.txt"));
        Files.deleteIfExists(Path.of("uploads", "second.txt"));
        Files.deleteIfExists(Path.of("uploads", "test.txt"));
    }

}