package com.product.biodata.repository;

import com.product.biodata.entity.BioData;
import com.product.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BioDataRepository extends JpaRepository<BioData,Long>, JpaSpecificationExecutor<BioData> {
}
