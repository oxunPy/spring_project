package uz.akfa.spring_project.service;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.akfa.spring_project.models.FileStorage;
import uz.akfa.spring_project.models.FileStorageStatus;
import uz.akfa.spring_project.repository.FileStorageRepository;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FileStorageService {

    private final FileStorageRepository fileStorageRepository;

    @Value("${upload.folder}")
    private String uploadFolder;

    private final Hashids hashids;

    public FileStorageService(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
        this.hashids = new Hashids(getClass().getName(), 6);
    }

    public void save(MultipartFile multipartFile){
        FileStorage fileStorage = new FileStorage();

        fileStorage.setFileName(multipartFile.getName());
        fileStorage.setFileSize(multipartFile.getSize());
        fileStorage.setContentType(multipartFile.getContentType());
        fileStorage.setStatus(FileStorageStatus.Draft);
        fileStorage.setExtension(getExt(multipartFile.getOriginalFilename()));
        System.out.println(multipartFile.getOriginalFilename());
        fileStorageRepository.save(fileStorage);            // saving into database

        Date now = new Date();
        File uploadFolder = new File(String.format("%s/upload_files/%d/%d/%d", this.uploadFolder, now.getYear() + 1900,
                                            now.getMonth(), now.getDay()));

        if(!uploadFolder.exists() && uploadFolder.mkdirs()){
            System.out.println("Aytilgan papkalar yaratildi.");
        }

        fileStorage.setHashId(hashids.encode(fileStorage.getId()));
        fileStorage.setUploadPath(String.format("upload_files/%d/%d/%d/%s.%s",
                1900 + now.getYear(),
                now.getMonth(),
                now.getDay(),
                fileStorage.getHashId(),
                fileStorage.getExtension()));

        fileStorageRepository.save(fileStorage);
        uploadFolder = uploadFolder.getAbsoluteFile();
        File file = new File(uploadFolder, String.format("%s.%s",
                                            fileStorage.getHashId(), fileStorage.getExtension()));

        try{
            multipartFile.transferTo(file);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public String getExt(String fileName){
        String ext = null;

        if(fileName != null && !fileName.isEmpty()){
            int dot = fileName.lastIndexOf(".");
            if(dot > 0 && dot <= fileName.length() - 2){
                ext = fileName.substring(dot + 1);
            }
        }
        return ext;
    }

    @Transactional(readOnly = true)
    public FileStorage findByHashId(String hashId){
        return fileStorageRepository.findByHashId(hashId);
    }

    public void delete(String hashId){
        FileStorage fileStorage = findByHashId(hashId);
        File file = new File(String.format("%s/%s", uploadFolder, fileStorage.getUploadPath()));
        if(file.delete()){
            fileStorageRepository.delete(fileStorage);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteAllDraft(){
        List<FileStorage> fileStorageList = fileStorageRepository.findAllByStorageStatusFileStorageStatus(FileStorageStatus.Draft);
        fileStorageList.stream().forEach(fileStorage -> delete(fileStorage.getHashId()));
    }
}
