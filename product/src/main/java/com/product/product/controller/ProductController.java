package com.product.product.controller;

import com.product.product.entity.Product;
import com.product.product.service.ProductService;
import helper.response.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import util.PaginatorService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    //create product
    @PostMapping("/create")
    public ResponseEntity<?> createTicket(@RequestBody Product entity) {

        return productService.create(entity);
    }
    //get request to get all products
    @GetMapping("/getList")
    public ResponseEntity<?> getAllPaginatedResponse(HttpServletRequest request,
                                                     @RequestParam Map<String,String> clientParams) {
        PaginatorService ps = new PaginatorService(request);
        Page<Product> page = this.productService.getAllPaginatedLists(clientParams, ps.pageNum, ps.pageSize, ps.sortField, ps.sortDir);
        List<Product> listData = page.getContent();

        return new ResponseEntity<>(new PaginatedResponse(true,200,"ok",page.getTotalElements(),
                page.getTotalPages(),ps.sortDir.equals("asc") ? "desc": "asc",page.getNumber(), Arrays.asList(listData.toArray())), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        return this.productService.getById(id);
    }
}
