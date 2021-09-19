package com.example.securingweb.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String text;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;


    public Message() {
    }

    public Message(String text,  User user) {
        this.text = text;

        this.author = user;
    }
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "tag_message",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags=new ArrayList<>();

    public void addTag(Tag tag){
        this.tags.add(tag);
        tag.getMessages().add(this);
    }
    public void removeTag(Tag tag){
        this.tags.remove(tag);
        tag.getMessages().remove(this);
    }

    @Override
    public int hashCode() {
        return 31;
    }
    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTagsStr() {
        String str="";
        for (Tag tag:tags){
            str = str + tag.getName() + ", ";
        }
        return str;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
