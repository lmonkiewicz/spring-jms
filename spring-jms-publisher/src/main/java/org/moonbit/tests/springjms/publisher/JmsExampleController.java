package org.moonbit.tests.springjms.publisher;

import org.moonbit.tests.springjms.model.Order;
import org.moonbit.tests.springjms.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Deam on 2016-12-12.
 */
@RestController
@RequestMapping("/publisher")
public class JmsExampleController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${outbound.orders}")
    private String ordersDestination;

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public ResponseEntity generate(){
        final Order order = Order.builder()
                .orderId(UUID.randomUUID().toString())
                .orderDate(new Date())
                .item(OrderItem.builder().name("First item").price(Math.random() * 10.0).build())
                .item(OrderItem.builder().name("Second item").price(Math.random() * 10.0).build())
                .item(OrderItem.builder().name("Third item").price(Math.random() * 10.0).build())
                .build();

        jmsTemplate.convertAndSend(ordersDestination, order);
        return ResponseEntity.ok(order);
    }
}
