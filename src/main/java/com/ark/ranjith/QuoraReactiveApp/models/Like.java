package com.ark.ranjith.QuoraReactiveApp.models;

import com.ark.ranjith.QuoraReactiveApp.enums.TargetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "likes")
public class Like {

    @Id
    private String id;

    private String targetId; // ID of the question or answer being liked

    private TargetType targetType; // QUESTION or ANSWER or COMMENT

    private Boolean isLike; // true for like, false for dislike

    @CreatedDate
    private LocalDateTime createdAt;
}
