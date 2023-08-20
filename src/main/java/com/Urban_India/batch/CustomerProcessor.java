package com.Urban_India.batch;

import com.Urban_India.batch.model.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CustomerProcessor implements ItemProcessor<Customer,Customer> {

    @Override
    public Customer process(Customer customer) throws Exception {
//        Integer date= Integer.parseInt(customer.getDob());
//        LocalDate date = LocalDate.parse(customer.getDob());
        return customer;
    }
}
