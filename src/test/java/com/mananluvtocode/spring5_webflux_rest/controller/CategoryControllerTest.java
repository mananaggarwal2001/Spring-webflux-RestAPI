package com.mananluvtocode.spring5_webflux_rest.controller;

import com.mananluvtocode.spring5_webflux_rest.domain.Category;
import com.mananluvtocode.spring5_webflux_rest.repositoriees.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class CategoryControllerTest {
    WebTestClient webTestClient;
    CategoryController categoryController;
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void findAll() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(
                        Flux.just(Category.builder()
                                        .description("new Category")
                                        .build(),
                                Category.builder()
                                        .description("This is new builder")
                                        .build(), Category.builder()
                                        .description("This is new builder for the category for doing the work.")
                                        .build()));

        webTestClient.get()
                .uri("/api/v1/categories")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Category.class)
                .hasSize(3);

    }

    @Test
    void getById() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("Find by id method is testing for doing the further work.").build()));
        webTestClient.get().uri("/api/v1/categories/manan")
                .exchange()
                .expectStatus().isOk();

    }
}