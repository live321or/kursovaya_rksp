package com.example.securingweb.repos;

import com.example.securingweb.domain.Message;
import com.example.securingweb.domain.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepo  extends CrudRepository<Tag, Long> {
    Tag findByName(String word);
}
