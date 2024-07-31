package com.mananluvtocode.spring5_webflux_rest.boostrap;

import com.mananluvtocode.spring5_webflux_rest.domain.Category;
import com.mananluvtocode.spring5_webflux_rest.domain.Vendor;
import com.mananluvtocode.spring5_webflux_rest.repositoriees.CategoryRepository;
import com.mananluvtocode.spring5_webflux_rest.repositoriees.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public DataLoader(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            if (categoryRepository.count().block() == null) {
                loadCategory();
            }
            if (vendorRepository.count().block() == null) {
                loadVendor();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading data");
        }
        System.out.println("Category loaded successfully:- " + categoryRepository.count().block());
        System.out.println("Vendor loaded successfully:- " + vendorRepository.count().block());
    }

    public void loadVendor() {
        Vendor manan = new Vendor();
        manan.setFirstName("Manan");
        manan.setLastName("Aggarwal");

        Vendor naman = new Vendor();
        naman.setFirstName("Naman");
        naman.setLastName("Super");

        Vendor vikaas = new Vendor();
        vikaas.setFirstName("Vikaas");
        vikaas.setLastName("Karl");

        Vendor vendor = new Vendor();
        vendor.setFirstName("Vendor");
        vendor.setLastName("Karl");

        vendorRepository.save(manan).block();
        vendorRepository.save(vikaas).block();
        vendorRepository.save(vendor).block();
        vendorRepository.save(naman).block();
    }

    public void loadCategory() {
        Category category = new Category();
        category.setDescription("This is my new description");
        categoryRepository.save(category).block();

        Category category1 = new Category();
        category1.setDescription("This is my new description 2");
        categoryRepository.save(category1).block();

        Category category2 = new Category();
        category2.setDescription("This is my new description 3");
        categoryRepository.save(category2).block();
    }
}
