package qrmi.adapter;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * Specifications for AMQP transport - producers and consumers.
 */
public interface AmqpTransport {
    /**
     * The AMQP exchange name.
     *
     * @return
     */
    String getExchange();

    /**
     * Exchange routing key.
     *
     * @return
     */
    String getRoutingKey();

    /**
     * Underlying template implementation.
     *
     * @return
     * @see org.springframework.amqp.rabbit.core.RabbitTemplate
     */
    AmqpTemplate getAmqpTemplate();

    /**
     * Transformation logic from/to domain models and AMQP message.
     *
     * @return
     */
    MessageConverter getMessageConverter();

    /**
     * Apply the underlying transport logic using the information exposed by the implementation.
     *
     * @param message
     * @param properties
     * @return
     * @throws AmqpException
     */
    Object apply(Object message, MessageProperties properties) throws AmqpException;
}
