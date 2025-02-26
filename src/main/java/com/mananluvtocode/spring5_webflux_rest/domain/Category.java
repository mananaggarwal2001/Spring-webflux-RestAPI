package com.mananluvtocode.spring5_webflux_rest.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    private String id;
    private String description;
}