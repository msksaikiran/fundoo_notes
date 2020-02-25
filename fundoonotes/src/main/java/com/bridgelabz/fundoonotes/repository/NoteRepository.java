package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.repository.CrudRepository;

import com.bridgelabz.fundoonotes.model.Noteinfo;


public interface NoteRepository extends CrudRepository<Noteinfo, String> 
{  

}  