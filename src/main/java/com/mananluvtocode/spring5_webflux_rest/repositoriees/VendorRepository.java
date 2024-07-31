package com.mananluvtocode.spring5_webflux_rest.repositoriees;

import com.mananluvtocode.spring5_webflux_rest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
    
}
