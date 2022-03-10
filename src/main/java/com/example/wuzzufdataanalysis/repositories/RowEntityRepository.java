package com.example.wuzzufdataanalysis.repositories;

import com.example.wuzzufdataanalysis.model.RowEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface RowEntityRepository extends CrudRepository<RowEntity, Long> {
    Object findAll(Pageable limit);
}
