package com.sevilay.repository;

import com.sevilay.repository.entity.ComputerSpec;
import com.sevilay.utility.MyFactoryRepository;

public class ComputerSpecRepository extends MyFactoryRepository<ComputerSpec, Long> {

    public ComputerSpecRepository() {
        super(new ComputerSpec());
    }

}
