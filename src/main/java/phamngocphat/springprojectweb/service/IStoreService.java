package phamngocphat.springprojectweb.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface IStoreService {
    public void openFileStore();
    public String storeFile(MultipartFile file);
    public Path uploadFile(String fileName);
    public Resource loadAsResource(String fileName);
    public void deleteFile(String fileName);
}
