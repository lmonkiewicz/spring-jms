package org.moonbit.tests.springjms.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.jms.ConnectionFactory;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = SpringJmsConsumerApplication.class)
@EnableJms
@EnableWebMvc
public class SpringJmsConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJmsConsumerApplication.class, args);
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	@Bean
	@Autowired
	public DefaultMessageListenerContainer notificationsTopicListenerContainer(
			@Value("${inbound.notifications.topic}") String destination,
			@Value("${inbound.notifications.subscription}") String subscription,
			@Value("${consumer.clientId}") String clientId,
			ConnectionFactory connectionFactory){

		final DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
		messageListenerContainer.setConnectionFactory(connectionFactory);
		messageListenerContainer.setDestinationName(destination);
		messageListenerContainer.setPubSubDomain(true);
		messageListenerContainer.setSessionAcknowledgeMode(JmsProperties.AcknowledgeMode.AUTO.getMode());

		// stuff for durable subscription
		messageListenerContainer.setSubscriptionDurable(true);
		messageListenerContainer.setDurableSubscriptionName(subscription);
		messageListenerContainer.setClientId(clientId);

		messageListenerContainer.setMessageListener(new NotificationsTopicListener());
		return messageListenerContainer;
	}
}
