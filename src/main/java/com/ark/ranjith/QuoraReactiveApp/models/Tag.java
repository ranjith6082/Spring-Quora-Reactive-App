package com.ark.ranjith.QuoraReactiveApp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tags")

public class Tag {
    @Id
    private String id;

    private String name;

    private List<String> questionIds; // many-to-many relation simulated
}
