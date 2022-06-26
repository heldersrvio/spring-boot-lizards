package com.helder.springbootlizards.repository;

import com.helder.springbootlizards.model.Lizard;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface LizardRepository extends CrudRepository<Lizard, String> { }