package org.moonbit.tests.springjms.consumer;

import org.moonbit.tests.springjms.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by lmonkiewicz on 2016-12-13.
 */
@Component
public class JmsNotificationsListener {

    private final static Logger LOG = LoggerFactory.getLogger(JmsNotificationsListener.class);

    /**
     * One message goes here
     * @param notification
     */
    @JmsListener(destination = "${inbound.notifications.queue}")
    public void onNotificationReceived(Notification notification){
        LOG.info("I received notification! {}", notification);
    }


    /**
     * Next one goes here, and next one goea back to the other one
     * @param notification
     */
    @JmsListener(destination = "${inbound.notifications.queue}")
    public void onNotificationReceivedDoSomethingElse(Notification notification){
        LOG.info("And now I received notification! {}", notification);
    }
}
