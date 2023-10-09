package com.sevilay.service;

import com.sevilay.repository.ComputerSpecRepository;
import com.sevilay.repository.entity.ComputerSpec;

import java.util.Optional;

public class ComputerSpecService {

    ComputerSpecRepository computerSpecRepository;

    public ComputerSpecService() {
        this.computerSpecRepository = new ComputerSpecRepository();

    }

    public ComputerSpec save(ComputerSpec computerSpec) {
        return computerSpecRepository.save(computerSpec);
    }

    public Optional<ComputerSpec> findById(Long computerSpecid) {
    return computerSpecRepository.findById(computerSpecid);

    }
}
