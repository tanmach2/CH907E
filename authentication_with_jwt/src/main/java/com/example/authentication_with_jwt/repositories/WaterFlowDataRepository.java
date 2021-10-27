package com.example.authentication_with_jwt.repositories;

import com.example.authentication_with_jwt.entities.WaterFlowData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaterFlowDataRepository extends CrudRepository<WaterFlowData, String> {
    List<WaterFlowData> findByDeviceId(int deviceId);
}
