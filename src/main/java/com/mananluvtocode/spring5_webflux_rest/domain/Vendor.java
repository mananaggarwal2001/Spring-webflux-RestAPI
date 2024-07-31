package com.mananluvtocode.spring5_webflux_rest.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Vendor {
    private String id;
    private String firstName;
    private String lastName;
}
