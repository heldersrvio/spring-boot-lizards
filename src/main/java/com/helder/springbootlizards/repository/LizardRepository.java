package com.helder.springbootlizards.repository;

import com.helder.springbootlizards.model.Lizard;
import org.springframework.data.repository.CrudRepository;

public interface LizardRepository extends CrudRepository<Lizard, String> { }