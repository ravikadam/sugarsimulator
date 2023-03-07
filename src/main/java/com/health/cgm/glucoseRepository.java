// Repository class for glucose

package com.health.cgm;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface glucoseRepository extends MongoRepository<glucose, String> {
    
    public glucose findByid(String id);
    public List<glucose> findByreading(int reading);
    public List<glucose> findByrecordDate(Date recordDate);
    public List<glucose> findAll();




}
