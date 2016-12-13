package org.moonbit.tests.springjms.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.jms.ConnectionFactory;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJms
@EnableWebMvc
public class SpringJmsPublisherApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJmsPublisherApplication.class, args);
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	@Bean(name = "jmsTemplateQueue")
	@Autowired
	public JmsTemplate jmsTemplateQueue(ConnectionFactory connectionFactory){
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory);
		template.setPubSubDomain(false);
		template.setMessageConverter(jacksonJmsMessageConverter());
		return template;
	}

	@Bean(name = "jmsTemplateTopic")
	@Autowired
	public JmsTemplate jmsTemplateTopic(ConnectionFactory connectionFactory){
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory);
		template.setPubSubDomain(true); // this makes it possible to publish to topic, instead of default queue
		template.setMessageConverter(jacksonJmsMessageConverter());
		return template;
	}
}
