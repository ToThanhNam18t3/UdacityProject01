package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS where userid=#{userid}")
    List<Credential> getAllCredentialsByUserId(Integer userId);

    @Delete("DELETE * FROM CREDENTIALS where credentialid=#{credentialid}")
    Credential deleteCredentialById(Integer credentialId);

    @Update("UPDATE CREDENTIALS set url={#url}, password={#}, username={#username} where credentialid={#credentialid}")
    int updateCredentialById(Credential credential);

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    int insertCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS where credentialid=#{credentialid}")
    Credential getCredentialById(Integer credentialId);

}
