package com.Urban_India.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressModel {

    private String google_location_code;
    private String state;
    private String city;
    private String pin;
    private String plotNo;

    @Override
    public String toString(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("google_location_code",this.google_location_code);
        jsonObject.put("state",this.state);
        jsonObject.put("city",this.city);
        jsonObject.put("pin",this.pin);
        jsonObject.put("plotNo",this.plotNo);
        return jsonObject.toString();
    }
}
