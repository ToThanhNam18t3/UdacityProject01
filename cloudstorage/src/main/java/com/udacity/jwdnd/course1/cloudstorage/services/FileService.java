package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMp;
    private final UserMapper userMp;

    public FileService(FileMapper fileMp, UserMapper userMp) {
        this.fileMp = fileMp;
        this.userMp = userMp;
    }

    public void deleteFile(Integer fileId){
        fileMp.deleteFile(fileId);
    }
    
    public List<File> getAllFilesByUserId(Authentication authen){
        String userName = authen.getName();
        User user = userMp.getUserByUsername(userName);
        return fileMp.getAllFilesByUserId(user.getUserId());
    }

    public File getFileByFieldId(Integer fileId) {
        return fileMp.getFileById(fileId);
    }


    public int uploadFile(Authentication authen, MultipartFile multipartFile) throws IOException {

        String userName = authen.getName();
        User user = userMp.getUserByUsername(userName);

//        File file = fileMp.getFileByName(multipartFile.getOriginalFilename(), user.getUserId());

        File file = new File();
        file.setFileName(multipartFile.getOriginalFilename());
        file.setContentType(multipartFile.getContentType());
        file.setFileSize(Long.toString(multipartFile.getSize()));
        file.setUserId(user.getUserId());
        file.setFileData(multipartFile.getBytes());

        return fileMp.insertFile(file);
    }
}
