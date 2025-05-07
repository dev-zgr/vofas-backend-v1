package org.example.vofasbackendv1.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE = "feedback.exchange";
    public static final String VOICE_QUEUE = "voice.queue";
    public static final String TEXT_QUEUE = "text.queue";
    public static final String VOICE_ROUTING_KEY = "feedback.voice";
    public static final String TEXT_ROUTING_KEY = "feedback.text";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue voiceQueue() {
        return new Queue(VOICE_QUEUE);
    }

    @Bean
    public Queue textQueue() {
        return new Queue(TEXT_QUEUE);
    }

    @Bean
    public Binding voiceBinding() {
        return BindingBuilder.bind(voiceQueue()).to(exchange()).with(VOICE_ROUTING_KEY);
    }

    @Bean
    public Binding textBinding() {
        return BindingBuilder.bind(textQueue()).to(exchange()).with(TEXT_ROUTING_KEY);
    }
}
