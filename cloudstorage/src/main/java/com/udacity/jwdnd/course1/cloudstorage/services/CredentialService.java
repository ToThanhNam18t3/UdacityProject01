package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final UserMapper userMp;
    private final CredentialMapper credentialMp;
    private EncrypService encrypService;
    private EncryptionService encryptionService;

    public CredentialService(UserMapper userMp, CredentialMapper credentialMp, EncrypService encrypService, EncryptionService encryptionService) {
        this.userMp = userMp;
        this.credentialMp = credentialMp;
        this.encrypService = encrypService;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentialsByUserId(Authentication authen) {
//        User user = userMp.getUserByUsername(authen.getName());
        String username = authen.getName();
        User user = userMp.getUserByUsername(username);

        List<Credential> credentialList = credentialMp.getAllCredentialsByUserId(user.getUserId());
        String passwordDcr;
        for (Credential credential : credentialList) {
            passwordDcr = encrypService.decryptValue(credential.getPassword(), credential.getKey());
            credential.setDecryptedPassword(passwordDcr);
        }
        return credentialList;
    }

    public void deleteCredential(Integer credentialId){
        credentialMp.deleteCredentialById(credentialId);
    }

    public void insertCredential(Authentication authen, CredentialDto credentialDto){
        User user = userMp.getUserByUsername(authen.getName());

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialDto.getPassword(), encodedKey);
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);

        Credential credential = new Credential();
        credential.setUserId(user.getUserId());
        credential.setUrl(credentialDto.getUrl());
        credential.setPassword(encryptedPassword);
        credential.setKey(encodedKey);
        credential.setUsername(credentialDto.getUserName());

        credentialMp.insertCredential(credential);
    }

    public void updateCredential(Authentication authen, CredentialDto credentialDto){
        Credential credential = credentialMp.getCredentialById(credentialDto.getCredentialId());
        String encryptedPassword = encryptionService.encryptValue(credentialDto.getPassword(), credential.getKey());
        credential.setUrl(credentialDto.getUrl());
        credential.setUsername(credentialDto.getUserName());
        credential.setPassword(encryptedPassword);

        credentialMp.updateCredentialById(credential);
    }
}
