package com.example.wuzzufdataanalysis.repositories;

import com.example.wuzzufdataanalysis.model.AreasEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface AreasEntityRepository extends CrudRepository<AreasEntity, Long> {
    Object findAll(Pageable limit);
}
