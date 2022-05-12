package com.product.biodata.controller;

import com.product.biodata.entity.BioData;
import com.product.biodata.service.BioDataService;
import com.product.product.entity.Product;
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
@RequestMapping("/bioData")
public class BioDataController {
    @Autowired
    private BioDataService bioDataService;

    @PostMapping("/create")
    public ResponseEntity<?> createTicket(@RequestBody BioData entity) {

        return bioDataService.create(entity);
    }

    @GetMapping("/getList")
    public ResponseEntity<?> getAllPaginatedResponse(HttpServletRequest request,
                                                     @RequestParam Map<String,String> clientParams) {
        PaginatorService ps = new PaginatorService(request);
        Page<BioData> page = this.bioDataService.getAllPaginatedLists(clientParams, ps.pageNum, ps.pageSize, ps.sortField, ps.sortDir);
        List<BioData> listData = page.getContent();

        return new ResponseEntity<>(new PaginatedResponse(true,200,"ok",page.getTotalElements(),
                page.getTotalPages(),ps.sortDir.equals("asc") ? "desc": "asc",page.getNumber(), Arrays.asList(listData.toArray())), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        return this.bioDataService.getById(id);
    }
}
