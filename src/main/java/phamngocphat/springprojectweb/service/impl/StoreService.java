package phamngocphat.springprojectweb.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import phamngocphat.springprojectweb.exception.FileNotFoudException;
import phamngocphat.springprojectweb.exception.StoreException;
import phamngocphat.springprojectweb.service.IStoreService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StoreService implements IStoreService {

    @Value("${storage.location}")
    private String storageLocation;

    @PostConstruct
    @Override
    public void openFileStore() {
        try{
            Files.createDirectories(Paths.get(storageLocation));
        }catch(IOException exception){
            throw new StoreException("Error with file");
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        String nameFile = file.getOriginalFilename();
        if(file.isEmpty()){
            throw new StoreException("Can't store an empty file");
        }
        try{
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, Paths.get(storageLocation).resolve(nameFile),
                    StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException exception){
            throw new StoreException("error storing file" + nameFile, exception);
        }
        return nameFile;
    }

    @Override
    public Path uploadFile(String fileName) {
        return Paths.get(storageLocation).resolve(fileName);
    }

    @Override
    public Resource loadAsResource(String fileName) {
        try{
            Path file = uploadFile(fileName);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()){
                return resource;
            }
            else{
                throw new FileNotFoudException("Could not find the file" + fileName);
            }
        }catch(MalformedURLException exception){
            throw new FileNotFoudException("Could not find the file" + fileName, exception);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        Path file = uploadFile(fileName);
        try{

            FileSystemUtils.deleteRecursively(file);
        }catch(Exception exception){
            System.out.println(exception);
        }
    }
}
