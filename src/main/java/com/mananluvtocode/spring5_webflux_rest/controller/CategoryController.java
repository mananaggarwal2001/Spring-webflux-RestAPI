package com.mananluvtocode.spring5_webflux_rest.controller;
import com.mananluvtocode.spring5_webflux_rest.domain.Category;
import com.mananluvtocode.spring5_webflux_rest.repositoriees.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/api/v1/categories")
    @ResponseStatus(HttpStatus.OK)
    Flux<Category> findAll() {
        return categoryRepository.findAll();
    }

    @GetMapping("/api/v1/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Category> getById(@PathVariable("id") String id) {
        return categoryRepository.findById(id);
    }
}
