package com.helder.springbootlizards.control;

import com.helder.springbootlizards.exception.LizardNotFoundException;
import com.helder.springbootlizards.model.Lizard;
import com.helder.springbootlizards.repository.LizardRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class LizardController {

    private final LizardRepository repository;

    LizardController(LizardRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/lizards")
    Iterable<Lizard> all() {
        return repository.findAll();
    }

    @PostMapping("/lizards")
    Lizard create(@RequestBody Lizard newLizard) {
        return repository.save(newLizard);
    }

    @GetMapping("/lizards/{id}")
    Lizard one(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> new LizardNotFoundException(id));
    }

    @DeleteMapping("/lizards/{id}")
    void delete(@PathVariable String id) {
        repository.deleteById(id);
    }

}
