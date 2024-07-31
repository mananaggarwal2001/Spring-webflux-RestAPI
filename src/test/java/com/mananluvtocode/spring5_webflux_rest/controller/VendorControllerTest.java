package com.mananluvtocode.spring5_webflux_rest.controller;
import com.mananluvtocode.spring5_webflux_rest.domain.Vendor;
import com.mananluvtocode.spring5_webflux_rest.repositoriees.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyString;

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
}