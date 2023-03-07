// Implementation and service class for glucose

package com.health.cgm;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class glucoseService {
    
    @Autowired
    private glucoseRepository glucoseRepository;
    
    public List<glucose> getAllGlucose() {
        return glucoseRepository.findAll();
    }
    
    public glucose getGlucose(String id) {
        return glucoseRepository.findByid(id);
    }

    public List<glucose> getGlucoseDt(Date recordDate) {
        return glucoseRepository.findByrecordDate(recordDate);
    }
    
    public void addGlucose(glucose glucose) {
        glucoseRepository.save(glucose);
    }
    
    public void updateGlucose(String id, glucose glucose) {
        glucoseRepository.save(glucose);
    }
    
    public void deleteGlucose(glucose glucose) {
        glucoseRepository.delete(glucose);
    }
}