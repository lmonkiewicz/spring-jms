package org.moonbit.tests.springjms.consumer;

import org.moonbit.tests.springjms.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lmonkiewicz on 2016-12-12.
 */
@RestController
@RequestMapping("/consumer")
public class JmsExampleController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${inbound.orders}")
    private String ordersDestination;


    @RequestMapping(value = "/consume", method = RequestMethod.GET)
    public ResponseEntity generate(){
        Order order = (Order)jmsTemplate.receiveAndConvert(ordersDestination);
        return ResponseEntity.ok(order);
    }
}
