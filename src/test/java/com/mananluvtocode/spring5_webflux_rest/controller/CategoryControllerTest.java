package com.mananluvtocode.spring5_webflux_rest.controller;

import com.mananluvtocode.spring5_webflux_rest.domain.Category;
import com.mananluvtocode.spring5_webflux_rest.repositoriees.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
        given(categoryRepository.findAll())
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
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("Find by id method is testing for doing the further work.").build()));
        webTestClient.get().uri("/api/v1/categories/manan")
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    void createNewCategory() {
        given(categoryRepository.saveAll((Publisher<Category>) any())).willReturn(Flux.just(Category.builder().build()));
        Mono<Category> categoryMono = Mono.just(Category.builder().description("This is the new Mono for doing the further work.").build());
        FluxExchangeResult<Category> categoryFlux = webTestClient.post().uri("/api/v1/categories")
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .returnResult(Category.class);
        System.out.println(categoryFlux.getResponseBody().blockFirst());
    }

    @Test
    void testUpdateCategory() {
        given(categoryRepository.save(any(Category.class))).willReturn(Mono.just(Category.builder().build()));
        Mono<Category> updatedCategory = Mono.just(Category.builder().id("newid").description("This is new Category").build());

        FluxExchangeResult<Category> categoryMonoReturnResult = webTestClient.put().uri("/api/v1/categories/newid")
                .body(updatedCategory, Category.class)
                .exchange().expectStatus().isOk().returnResult(Category.class);
        System.out.println(categoryMonoReturnResult.getResponseBody().then().block());
    }

    @Test
    void testPatchUpdateCategory() {
        // this is producing the null pointer exception error now we will do one thing that we will use the method which is findbyId for gurantee that this will return the object.
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));
        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));
        Mono<Category> updatedCategory = Mono.just(Category.builder().id("newid").description("this is new description").build());

        webTestClient.patch().uri("/api/v1/categories/newid")
                .body(updatedCategory, Category.class)
                .exchange().expectStatus().isOk();

        verify(categoryRepository).save(any(Category.class));
    }
}