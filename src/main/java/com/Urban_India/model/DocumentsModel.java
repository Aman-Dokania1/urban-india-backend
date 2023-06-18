package com.Urban_India.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsModel {
    private String gstinNumber;

   @Override
   public String toString(){
       JSONObject jsonObject = new JSONObject();
       jsonObject.put("gstinNumber",this.gstinNumber);
       return jsonObject.toString();
   }
}
