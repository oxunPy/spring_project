package uz.akfa.spring_project.models;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class FileStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private Long fileSize;

    private String extension;

    private String hashId;

    private String contentType;

    private String uploadPath;

    @Enumerated(EnumType.STRING)
    private FileStorageStatus status;

}
