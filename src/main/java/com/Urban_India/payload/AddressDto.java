package com.Urban_India.payload;

import com.Urban_India.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.json.JSONObject;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AddressDto {
    private Long id; 
    private String google_location_code;
    private String state;
    private String city;
    private String pin;
    private String plotNo;

    public Address toAddress(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("google_location_code",this.google_location_code);
        jsonObject.put("state",this.state);
        jsonObject.put("city",this.city);
        jsonObject.put("pin",this.pin);
        jsonObject.put("plotNo",this.plotNo);
        String address = jsonObject.toString();
        return Address.builder().address(address).build();
    }
}
