package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId){
        fileService.deleteFile(fileId);
        return "redirect:/home";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(Model model, Authentication authen,@RequestParam(value="fileUploaded", required=false) MultipartFile fileUploaded, RedirectAttributes attributes){

        // Check upload file empty or not
        if(fileUploaded.isEmpty()){
            attributes.addFlashAttribute("message", "Please select file to up load !");
            return "redirect:/home";
        }

        // Main handle
        try {
            int uploadedFile = fileService.uploadFile(authen, fileUploaded);
            if(uploadedFile != 0) {
                model.addAttribute("fileUploadedSuccess", true);
                model.addAttribute("fileUploadedSuccessMessage", "Upload file succeed");
            } else {
                model.addAttribute("fileUploadedFail", true);
                model.addAttribute("fileUploadedFailMessage", "File name is already existed.");
            }
        } catch (IOException e) {
            model.addAttribute("fileUploadError", true);
            model.addAttribute("fileUploadErrorMessage", "Server is not response, please try again !");
            e.printStackTrace();
        }

        return "result";

    };

    @GetMapping("/file/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Integer fileId) {
        File file = fileService.getFileByFieldId(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getFileData());
    }


}
