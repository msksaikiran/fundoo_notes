//package com.bridgelabz.fundoonote.serviceimplementation;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bridgelabz.fundoonote.entity.Noteinfo;
//import com.bridgelabz.fundoonote.entity.Users;
//import com.bridgelabz.fundoonote.repository.SearchResult;
//import com.bridgelabz.fundoonote.service.NoteService;
//
//@Component
//public class Loaders {
//
//    @Autowired
//    ElasticsearchOperations operations;
//
//    @Autowired
//    SearchResult usersRepository;
//
//    @Autowired
//	private NoteService noteService;
//    
//    @PostConstruct
//    @Transactional
//    public void loadAll(){
//
//        operations.putMapping(Noteinfo.class);
//        System.out.println("Loading Data");
//        usersRepository.saveAll(this.getData());
//        System.out.printf("Loading Completed");
//
//    }
//
//    private List<Users> getData() {
//        List<Users> userses = new ArrayList<>();
//        userses.add(new Users("Ajay",123L, "Accounting", 12000L));
//        userses.add(new Users("Jaga",1234L, "Finance", 22000L));
//        userses.add(new Users("Thiru",1235L, "Accounting", 12000L));
//        return userses;
//    }
//}