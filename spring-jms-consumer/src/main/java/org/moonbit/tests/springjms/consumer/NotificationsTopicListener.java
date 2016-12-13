package org.moonbit.tests.springjms.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.moonbit.tests.springjms.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * Created by lmonkiewicz on 2016-12-13.
 */

public class NotificationsTopicListener implements MessageListener{

    private final static Logger LOG = LoggerFactory.getLogger(NotificationsTopicListener.class);

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            try {
                Notification notification = new ObjectMapper().readValue(textMessage.getText(), Notification.class);
                LOG.info("I got notification from Topic! {}", notification);
            } catch (IOException | JMSException e) {
                LOG.error("Something went wrong :(", e);
            }
        }
    }
}
