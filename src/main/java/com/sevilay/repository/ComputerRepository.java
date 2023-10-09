package com.sevilay.repository;

import com.sevilay.repository.entity.Computer;
import com.sevilay.utility.MyFactoryRepository;

public class ComputerRepository extends MyFactoryRepository<Computer, Long> {

    public ComputerRepository() {
        super(new Computer());
    }
}
