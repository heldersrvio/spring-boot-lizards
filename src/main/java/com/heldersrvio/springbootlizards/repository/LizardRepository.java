package com.heldersrvio.springbootlizards.repository;

import com.heldersrvio.springbootlizards.model.Lizard;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface LizardRepository extends CrudRepository<Lizard, String> {
    @EnableScan
    List<Lizard> findBySpecies(String species);
    @EnableScan
    List<Lizard> findByCommonName(String commonName);
}