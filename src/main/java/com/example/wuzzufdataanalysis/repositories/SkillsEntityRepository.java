package com.example.wuzzufdataanalysis.repositories;

import com.example.wuzzufdataanalysis.model.SkillsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface SkillsEntityRepository extends CrudRepository<SkillsEntity, Long> {
    Object findAll(Pageable limit);
}
