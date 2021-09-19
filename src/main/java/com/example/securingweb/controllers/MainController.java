package com.example.securingweb.controllers;


import com.example.securingweb.domain.Message;
import com.example.securingweb.domain.Tag;
import com.example.securingweb.domain.User;
import com.example.securingweb.repos.MessageRepo;
import com.example.securingweb.repos.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private TagRepo tagRepo;

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model) {
        Iterable<Message> messages = messageRepo.findAll();

        model.addAttribute("messages", messages);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Model model) {
        Message message = new Message(text, user);

        if (!tag.isEmpty()) {
            String[] words = tag.split(" ");
            for (String word : words) {
                Tag findTag = tagRepo.findByName(word);
                if (findTag != null)
                    message.addTag(findTag);
                else
                    message.addTag(new Tag(word));
            }
        }
        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();

        model.addAttribute("messages", messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages;

        List<Message> smessages = new ArrayList<>();
        List<Message> fmessages = new ArrayList<>();

        String[] worlds = filter.split(" ");

        fmessages = messageRepo.fAll();
        
        if (filter != null && !filter.isEmpty()) {
            for (String word : worlds) {

                for (int i = 0; i < fmessages.size(); i++) {
                    for (int b = 0; b < fmessages.get(i).getTags().size(); b++) {
                        if (fmessages.get(i).getTags().get(b) == tagRepo.findByName(word)) {
                            smessages.add(fmessages.get(i));
                        }
                    }
                }
                fmessages.clear();
                fmessages.addAll(smessages);
                smessages.clear();
            }
            messages = fmessages;
        } else {
            messages = messageRepo.findAll();
        }

        model.put("messages", messages);

        return "main";
    }
}

