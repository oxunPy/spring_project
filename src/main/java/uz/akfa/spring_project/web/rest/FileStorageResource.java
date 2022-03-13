package uz.akfa.spring_project.web.rest;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.akfa.spring_project.models.FileStorage;
import uz.akfa.spring_project.service.FileStorageService;

import java.net.MalformedURLException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api")
public class FileStorageResource {

    private final FileStorageService fileStorageService;

    @Value("${upload.folder}")
    private String uploadFolder;

    public FileStorageResource(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile multipartFile){
        fileStorageService.save(multipartFile);
        return ResponseEntity.ok(multipartFile.getOriginalFilename() + "fayl yuklandi.");
    }


    // reading file
    @GetMapping("/preview/{hashId}")
    public ResponseEntity previewFile(@PathVariable String hashId) throws MalformedURLException {
        FileStorage fileStorage = fileStorageService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\"" + URLEncoder.encode(fileStorage.getFileName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", uploadFolder, fileStorage.getUploadPath())));
    }

    // copying file
    @GetMapping("/download/{hashId}")
    public ResponseEntity downloadFile(@PathVariable String hashId) throws MalformedURLException {
        FileStorage fileStorage = fileStorageService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + URLEncoder.encode(fileStorage.getFileName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", uploadFolder, fileStorage.getUploadPath())));
    }


    @DeleteMapping("/delete/{hashId}")
    public ResponseEntity delete(@PathVariable String hashId){
        fileStorageService.delete(hashId);
        return ResponseEntity.ok("File has been deleted successfully");
    }
}
