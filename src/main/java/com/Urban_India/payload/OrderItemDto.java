package com.Urban_India.payload;

import com.Urban_India.Enum.OrderItemStatusEnum;
import com.Urban_India.Enum.OrderStatusEnum;
import com.Urban_India.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderItemDto {
    private Long id;
    private Long businessServiceId;
    private String businessServiceName;
    private Double businessServicePrice;
    private Long quantity;
    private LocalDate completionDate;
    private OrderItemStatusEnum status;

    public OrderItem toOrdertItem(){
        return OrderItem.builder()
                .id(this.id)
                .businessServiceName(this.businessServiceName)
                .businessServiceId(this.businessServiceId)
                .completionDate(this.completionDate)
                .quantity(this.quantity)
                .businessServicePrice(this.businessServicePrice)
                .orderItemStatusEnum(this.status)
                .build();
    }

}
