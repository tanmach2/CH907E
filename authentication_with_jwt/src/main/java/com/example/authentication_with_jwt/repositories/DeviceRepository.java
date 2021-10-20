package com.example.authentication_with_jwt.repositories;

import com.example.authentication_with_jwt.entities.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Integer> {
    public Device findBySeri(String serial);
}
