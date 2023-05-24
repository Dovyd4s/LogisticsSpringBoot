package com.logisticsspring.controllers;
import com.google.gson.Gson;
import com.logisticsspring.entities.Comment;
import com.logisticsspring.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Properties;

@RestController
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    public CommentController(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @GetMapping(value = "/getComment/{id}")
    public @ResponseBody Comment getCommentById (@PathVariable(name = "id")int id){
        Comment comment = commentRepository.findById(id).orElse(null);
        return comment;
    }

    @DeleteMapping(value = "/deleteComment/{id}")
    public @ResponseBody String deleteComment (@PathVariable(name = "id")int id){
        commentRepository.deleteById(id);
        if(commentRepository.existsById(id)){
            return "No success";
        }else return "OK";
    }

    @PostMapping(value = "/addComment")
    public @ResponseBody Comment addTrip (@RequestBody String info){
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);
        Comment comment = new Comment();
        comment.setCommentText(properties.getProperty("comment"));
        comment.setCreatedDateTime(LocalDateTime.now());
        comment = commentRepository.save(comment);
        return comment;
    }
}
