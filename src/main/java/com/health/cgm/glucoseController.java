
package com.health.cgm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class glucoseController {
    
    @Autowired
    private glucoseService glucoseService;
    
    @RequestMapping("/glucose")
    public List<glucose> getAllGlucose() {
        return glucoseService.getAllGlucose();
    }
    
    @RequestMapping("/glucose/{id}")
    public glucose getGlucose(@PathVariable String id) {
        return glucoseService.getGlucose(id);
    }
    


    // Get Sugar records for given recordDate which is date field
    @RequestMapping("/glucosedt/{recordDatestr}")
    public List<glucose> getGlucoseDt(@PathVariable String recordDatestr) {
        //convert to date
        Date recordDate = null;

        try {
            recordDate = new SimpleDateFormat("yyyy-MM-dd").parse(recordDatestr);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("recordDate: " + recordDate);
        return glucoseService.getGlucoseDt(recordDate);

    }


    
    @RequestMapping(method=RequestMethod.POST, value="/glucose")
    public void addGlucose(@RequestBody glucose glucose) {
        glucoseService.addGlucose(glucose);
    }
    
    @RequestMapping(method=RequestMethod.PUT, value="/glucose/{id}")
    public void updateGlucose(@RequestBody glucose glucose, @PathVariable String id) {
        glucoseService.updateGlucose(id, glucose);
    }
    
    @RequestMapping(method=RequestMethod.DELETE, value="/glucose/{id}")
    public void deleteGlucose(@PathVariable glucose glucose) {
        glucoseService.deleteGlucose(glucose);
    }
}