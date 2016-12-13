package org.moonbit.tests.springjms.publisher;

import org.moonbit.tests.springjms.model.Notification;
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
 * Created by lmonkiewicz on 2016-12-12.
 */
@RestController
@RequestMapping("/publisher")
public class JmsExampleController {

    @Autowired
    private JmsTemplate jmsTemplateQueue;
    @Autowired
    private JmsTemplate jmsTemplateTopic;

    @Value("${outbound.orders}")
    private String ordersDestination;

    @Value("${outbound.orders2}")
    private String orders2Destination;

    @Value("${outbound.notifications}")
    private String notificationsDestination;

    @RequestMapping(value = "/order1/generate", method = RequestMethod.GET)
    public ResponseEntity generate1(){
        final Order order = createOrder();
        jmsTemplateQueue.convertAndSend(ordersDestination, order);
        return ResponseEntity.ok(order);
    }

    @RequestMapping(value = "/order2/generate", method = RequestMethod.GET)
    public ResponseEntity generate2(){
        final Order order = createOrder();
        jmsTemplateQueue.convertAndSend(orders2Destination, order);
        return ResponseEntity.ok(order);
    }

    /**
     * This will publish to Queue, and create this queue if it does not exist
     * @return
     */
    @RequestMapping(value = "/notification/generate", method = RequestMethod.GET)
    public ResponseEntity notification(){
        final Notification notification = Notification.builder()
                                            .id(UUID.randomUUID().toString())
                                            .date(new Date())
                                            .title("Stuff happened!")
                                            .build();

        jmsTemplateQueue.convertAndSend(notificationsDestination, notification);
        return ResponseEntity.ok(notification);
    }

    /**
     * This will publish to Topic
     * @return
     */
    @RequestMapping(value = "/notificationTopic/generate", method = RequestMethod.GET)
    public ResponseEntity notificationTopic(){
        final Notification notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .date(new Date())
                .title("Stuff happened!")
                .build();

        jmsTemplateTopic.convertAndSend(notificationsDestination, notification);
        return ResponseEntity.ok(notification);
    }

    private Order createOrder() {
        return Order.builder()
                .orderId(UUID.randomUUID().toString())
                .orderDate(new Date())
                .item(OrderItem.builder().name("First item").price(Math.random() * 10.0).build())
                .item(OrderItem.builder().name("Second item").price(Math.random() * 10.0).build())
                .item(OrderItem.builder().name("Third item").price(Math.random() * 10.0).build())
                .build();
    }
}
