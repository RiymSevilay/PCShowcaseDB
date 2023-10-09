package com.sevilay.service;

import com.sevilay.repository.ComputerRepository;
import com.sevilay.repository.entity.Computer;
import com.sevilay.repository.entity.User;

import java.util.Optional;

public class ComputerService {



    ComputerRepository computerRepository;

    public ComputerService() {
        this.computerRepository = new ComputerRepository();

    }

    public Computer save(Computer computer) {
        return computerRepository.save(computer);

    }

    public Optional<Computer> findById(Long computerid) {
        return computerRepository.findById(computerid);
    }
}
