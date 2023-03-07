package com.health.cgm;

import java.util.Date;

// MongoDB Model class in spring boot

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "glucose")

public class glucose {
    
    @Id
    private String id;
   //record_date is date timefield
   @Field("record_date")
    private Date recordDate;
    private int reading;
    
    public glucose() {
        super();
    }
    
    public glucose(String id, Date recordDate, int reading) {
        super();
        this.id = id;
        this.recordDate = recordDate;
        this.reading = reading;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Date getRecordDate() {
        return recordDate;
    }
    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public int getReading() {
        return reading;
    }
    public void setReading(int reading) {
        this.reading = reading;
    }

    
}









