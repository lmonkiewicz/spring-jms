package org.moonbit.tests.springjms.model;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Date;
import java.util.List;

/**
 * Created by Deam on 2016-12-12.
 */
@Data
public class Order {
    private Date orderDate;
    private String orderId;
    private List<OrderItem> items;

    public Order() {
    }

    @Builder
    public Order(Date orderDate, String orderId, @Singular List<OrderItem> items){
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.items = items;
    }

}
