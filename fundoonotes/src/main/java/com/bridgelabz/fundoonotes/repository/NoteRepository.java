package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.repository.CrudRepository;

import com.bridgelabz.fundoonotes.entity.Noteinfo;


public interface NoteRepository extends CrudRepository<Noteinfo, String> 
{  

}  