package com.logisticsspring.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
public class Comment {
    @Id
    private int id;
    @OneToOne
    private User author;
    private LocalDateTime createdDateTime;
    private String commentText;
    @OneToMany (mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies;
    @ManyToOne
    private Comment parentComment;
}
