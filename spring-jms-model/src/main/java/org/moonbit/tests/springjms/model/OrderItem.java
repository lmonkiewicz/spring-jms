package org.moonbit.tests.springjms.model;

import lombok.Builder;
import lombok.Data;

/**
 * Created by lmonkiewicz on 2016-12-12.
 */
@Data
public class OrderItem {
    private String name;
    private Double price;

    public OrderItem() {
    }

    @Builder
    public OrderItem(String name, Double price){
        this.name = name;
        this.price = price;
    }
}
