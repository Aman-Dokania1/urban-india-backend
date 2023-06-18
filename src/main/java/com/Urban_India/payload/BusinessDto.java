package com.Urban_India.payload;

import com.Urban_India.entity.Address;
import com.Urban_India.entity.Business;
import com.Urban_India.entity.Image;
import com.Urban_India.entity.User;
import com.Urban_India.model.AddressModel;
import com.Urban_India.model.DocumentsModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDto {
    private Long id;
    private String name;
    private String tagline;
    private Image image;
    private AddressModel addressModel;
    private DocumentsModel documentsModel;


    public Business toBusiness(){
        return Business.builder()
                .id(this.id)
                .name(this.name)
                .tagline(this.tagline)
                .image(this.image)
                .address(new Address(null,addressModel.toString()))
                .documents(this.documentsModel.toString()).build();
    }
}
