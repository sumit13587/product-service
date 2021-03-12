package org.sumits.product.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.sumits.product.model.Product;

import javax.persistence.Id;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {}
