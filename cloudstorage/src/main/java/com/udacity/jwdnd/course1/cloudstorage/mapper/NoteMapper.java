package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNoteById(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getAllNotes(Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    int deleteNoteByNoteId(Integer noteId);

    @Insert("INSERT INTO NOTES(notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    int updateNote(Note note);




}
