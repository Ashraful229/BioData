package com.product.biodata.service;

import com.product.biodata.entity.BioData;
import com.product.biodata.repository.BioDataRepository;
import com.product.product.entity.Product;
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

import javax.persistence.criteria.Predicate;
import java.util.Map;
import java.util.Optional;

@Service
public class BioDataService {
    @Autowired
    private BioDataRepository bioDataRepository;

    public ResponseEntity<?> create(BioData entity) {
        try {
            BioData p = this.bioDataRepository.save(entity);
            return new ResponseEntity<>(new BaseResponse(true, "Bio data created successfully", 201,p), HttpStatus.OK);

        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(false, "Bio data creation failed", 400), HttpStatus.OK);
        }
    }

    public Page<BioData> getAllPaginatedLists(Map<String, String> clientParams, int pageNum, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<BioData> entities = bioDataRepository.findAll((Specification<BioData>) (root, cq, cb) -> {

            Predicate p = cb.conjunction();

            return p;
        }, pageable);

        return entities;
    }

    public ResponseEntity<?> getById(Long id) {
        try {
            Optional<BioData> entity = bioDataRepository.findById(id);
            if (entity.isPresent()) {
                BioData p = entity.get();
                return new ResponseEntity<>(new BaseResponse(true, "Bio data found successfully", 200, p), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new BaseResponse(false, "Bio data not found", 404), HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new BaseResponse(false, "Something went wrong: "+e.getMessage(), 500), HttpStatus.OK);
        }
    }
}
