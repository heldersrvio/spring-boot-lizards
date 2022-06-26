package com.heldersrvio.springbootlizards.control;

import com.heldersrvio.springbootlizards.exception.LizardNotFoundException;
import com.heldersrvio.springbootlizards.model.Lizard;
import com.heldersrvio.springbootlizards.repository.LizardRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class LizardController {

    private final LizardRepository repository;

    LizardController(LizardRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/lizards")
    Iterable<Lizard> all(@RequestParam(required = false) String species, @RequestParam(required = false) String commonName) {
        if (species != null) {
            return repository.findBySpecies(species);
        } else if (commonName != null) {
            return repository.findByCommonName(commonName);
        }
        return repository.findAll();
    }
    @GetMapping("/lizards/{id}")
    Lizard one(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> new LizardNotFoundException(id));
    }
    @PostMapping("/lizards")
    Lizard create(@RequestBody Lizard newLizard) {
        return repository.save(newLizard);
    }

    @DeleteMapping("/lizards/{id}")
    void delete(@PathVariable String id) {
        repository.deleteById(id);
    }

}
