package com.github.pedroluiznogueira.microservicesproduct.repository;

import com.github.pedroluiznogueira.microservicesproduct.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Optional<Product> findByCode(String code);
}
