package com.product.product.service;

import com.product.product.entity.Product;
import com.product.product.repository.ProductRepository;
import helper.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public ResponseEntity<?> create(Product entity) {
        try {
            Product p = this.productRepository.save(entity);
            return new ResponseEntity<>(new BaseResponse(true, "Product created successfully", 201,p), HttpStatus.OK);

        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(false, "Product creation failed", 400), HttpStatus.OK);
        }
    }

    public Page<Product> getAllPaginatedLists(Map<String, String> clientParams, int pageNum, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<Product> entities = productRepository.findAll((Specification<Product>) (root, cq, cb) -> {

            Predicate p = cb.conjunction();

            return p;
        }, pageable);

        return entities;
    }

    public ResponseEntity<?> getById(Long id) {
        try {
            Optional<Product> entity = productRepository.findById(id);
            if (entity.isPresent()) {
                Product p = entity.get();
                return new ResponseEntity<>(new BaseResponse(true, "Product found successfully", 200, p), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new BaseResponse(false, "Product not found", 404), HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new BaseResponse(false, "Something went wrong: "+e.getMessage(), 500), HttpStatus.OK);
        }
    }
}
