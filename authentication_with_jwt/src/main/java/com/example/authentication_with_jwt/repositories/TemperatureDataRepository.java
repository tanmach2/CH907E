package com.example.authentication_with_jwt.repositories;

import com.example.authentication_with_jwt.entities.Role;
import com.example.authentication_with_jwt.entities.TemperatureData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// How to write custom method in repository in Spring Data JPA
// https://www.netsurfingzone.com/jpa/how-to-write-custom-method-in-repository-in-spring-data-jpa/

@Repository
public interface TemperatureDataRepository extends CrudRepository<TemperatureData, String> {
    List<TemperatureData> findByDeviceId(int deviceId);

    List<TemperatureData> findByDeviceIdAndDeviceTimeGreaterThanAndDeviceTimeLessThan(int deviceId, long startTime, long endTime);
}
