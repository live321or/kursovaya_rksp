package com.example.securingweb.repos;


import com.example.securingweb.domain.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Long> {

//    List<Message> findByTag(String tag);

    @Query(value = "SELECT * FROM Message m " +
            "JOIN tag_message on m.id = tag_message.message_id " +
            "JOIN tag ON tag.id = tag_message.tag_id "+
            "Where tag.name IN :names  " +
            "GROUP BY m.id "  ,
            nativeQuery = true)
    List<Message> filter(@Param("names") Collection<String> names);


    @Query(value = "SELECT * FROM Message m " +
            "JOIN tag_message on m.id = tag_message.message_id " +
            "JOIN tag ON tag.id = tag_message.tag_id "+
            "GROUP BY m.id "  ,
            nativeQuery = true)
    List<Message> fAll();
}
