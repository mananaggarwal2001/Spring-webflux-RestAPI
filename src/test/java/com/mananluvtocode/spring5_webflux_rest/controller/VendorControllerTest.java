package com.mananluvtocode.spring5_webflux_rest.controller;

import com.mananluvtocode.spring5_webflux_rest.domain.Vendor;
import com.mananluvtocode.spring5_webflux_rest.repositoriees.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

class VendorControllerTest {
    VendorController vendorController;
    VendorRepository vendorRepository;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);

        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void getAllVendors() {
        // BDD means Behaviour driven development of the Mockito that is used for doing the test for the endpoints.
        BDDMockito.given(vendorRepository.findAll()).willReturn(
                Flux.just(Vendor.builder().firstName("manan").build()
                        , Vendor.builder().firstName("jay").build()
                        , Vendor.builder().firstName("jay").build()
                        , Vendor.builder().firstName("jayaHay").build()));

        EntityExchangeResult<List<Vendor>> vendorFlux = webTestClient.get().uri("/api/v1/vendors")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Vendor.class)
                .hasSize(4)
                .returnResult();
        System.out.println(vendorFlux.getResponseBody());
    }

    @Test
    void getVendorById() {
        // BDD:- Behaviour driven development for doing the testing the endpoints.
        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().firstName("GivenName").build()));
        EntityExchangeResult<Vendor> vendorMono = webTestClient.get().uri("/api/v1/vendors/givenName")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Vendor.class)
                .returnResult();
        System.out.println(vendorMono.getResponseBody());
    }

    @Test
    void testCreateNewVendor() {
        BDDMockito.given(vendorRepository
                        .saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));
        Mono<Vendor> vendorMock = Mono.just(Vendor.builder().firstName("New").lastName("Vendor").build());
        FluxExchangeResult<Vendor> vendorFluxExchangeResult = webTestClient.post().uri("/api/v1/vendors")
                .body(vendorMock, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .returnResult(Vendor.class);
        System.out.println(vendorFluxExchangeResult.getResponseBody().blockFirst());
    }

    @Test
    void testUpdateVendorById() {
        BDDMockito.given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build()));
        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("This is my new vendor").build());
        EntityExchangeResult<Vendor> vendorResult = webTestClient.put().uri("/api/v1/vendors/givenName")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Vendor.class)
                .returnResult();
        System.out.println(vendorResult.getResponseBody());

        verify(vendorRepository).save(any(Vendor.class));
    }

    @Test
    void testPatchVendorByIdWithChanges() {
        BDDMockito.given(vendorRepository.findById(anyString())).willReturn(Mono.just(Vendor.builder().firstName("Man").build()));
        BDDMockito.given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build()));
        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Manan").build());

        webTestClient.put().uri("/api/v1/vendors/givenName").body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus().isOk();

        verify(vendorRepository).save(any(Vendor.class));
    }

    @Test
    void testPatchVendorByIdWithoutChanges() {
        BDDMockito.given(vendorRepository.findById(anyString())).willReturn(Mono.just(Vendor.builder().firstName("Manan").build()));
        BDDMockito.given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build()));
        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Manan").build());

        webTestClient.put().uri("/api/v1/vendors/givenName").body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus().isOk();

        verify(vendorRepository).save(any(Vendor.class));
    }
}