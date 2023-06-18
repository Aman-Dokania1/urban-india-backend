package com.Urban_India.entity;

import com.Urban_India.model.AddressModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    public Address(Long id,String address){
        this.id = id;
        this.address = address;
    }
//    private String google_location_code;
//    private String state;
//    private String city;
//    private String pin;
//    private String plotNo;

    @Transient
    AddressModel addressModel;


    @PostLoad
    public void postLoad(){
        if(Objects.nonNull(this.address)){
            JSONObject address  = new JSONObject(this.address);
            String google_location_code = address.has("google_location_code") ? address.getString("google_location_code") : null;
            String state = address.has("state") ? address.getString("state") : null;
            String city = address.has("city") ? address.getString("city") : null;
            String pin = address.has("pin") ? address.getString("pin") : null;
            String plotNo = address.has("plotNo") ? address.getString("plotNo") : null;

            this.addressModel = new AddressModel(google_location_code,state,city,pin,plotNo);
        }
    }
}
