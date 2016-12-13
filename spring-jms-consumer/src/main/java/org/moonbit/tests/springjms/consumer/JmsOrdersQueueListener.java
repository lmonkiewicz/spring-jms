package org.moonbit.tests.springjms.consumer;

import org.moonbit.tests.springjms.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by lmonkiewicz on 2016-12-13.
 */
@Component
public class JmsOrdersQueueListener {

    private final static Logger LOG = LoggerFactory.getLogger(JmsOrdersQueueListener.class);

    @Value("${inbound.orders2")
    private String orders2Destination;


    @JmsListener(destination = "${inbound.orders2}")
    public void onNewOrder(Order order){
        LOG.info("Order received: {}", order);
    }
}
