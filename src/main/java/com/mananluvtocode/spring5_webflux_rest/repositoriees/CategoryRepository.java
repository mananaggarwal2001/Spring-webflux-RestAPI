package com.mananluvtocode.spring5_webflux_rest.repositoriees;

import com.mananluvtocode.spring5_webflux_rest.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {

}