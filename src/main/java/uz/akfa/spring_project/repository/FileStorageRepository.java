package uz.akfa.spring_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.akfa.spring_project.models.FileStorage;
import uz.akfa.spring_project.models.FileStorageStatus;

import java.util.List;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {

    FileStorage findByHashId(String hashId);

    List<FileStorage> findAllByStorageStatusFileStorageStatus(FileStorageStatus fileStorageStatus);
}
