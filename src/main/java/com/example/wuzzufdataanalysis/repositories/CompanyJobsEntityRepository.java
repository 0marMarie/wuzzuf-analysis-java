package com.example.wuzzufdataanalysis.repositories;

import com.example.wuzzufdataanalysis.model.CompanyJobsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CompanyJobsEntityRepository extends CrudRepository<CompanyJobsEntity, Long> {
    Object findAll(Pageable limit);
}
