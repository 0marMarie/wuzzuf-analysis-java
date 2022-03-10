package com.example.wuzzufdataanalysis.repositories;

import com.example.wuzzufdataanalysis.model.JobTitleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface JobTitleEntityRepository extends CrudRepository<JobTitleEntity, Long> {
    Object findAll(Pageable limit);
}
