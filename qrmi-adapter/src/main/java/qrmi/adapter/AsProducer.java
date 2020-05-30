package qrmi.adapter;

import java.util.Optional;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * The default model that defines AMQP transport logic for producers/publishers.
 *
 */
public class AsProducer implements AmqpTransport {
    private MessageConverter messageConverter;
    private AmqpTemplate amqpTemplate;
    private String routingKey;
    private String exchange;

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Override
    public String getRoutingKey() {
        return routingKey;
    }

    @Override
    public MessageConverter getMessageConverter() {
        return messageConverter;
    }

    @Override
    public AmqpTemplate getAmqpTemplate() {
        return amqpTemplate;
    }

    @Override
    public String getExchange() {
        return exchange;
    }

    /**
     * For producers/pubishers, the {@link #messageConverter} will be used to convert the domain-specific model into the
     * AMQP message that will be broadcast by the {@link #amqpTemplate}.
     *
     * @param message
     * @param properties
     * @return true if the message was published; false otherwise.
     * @throw AmqpException on any transport errors.
     */
    @Override
    public Object apply(Object message, MessageProperties properties) {
        if(amqpTemplate == null) {
            throw new IllegalStateException("Cannot publish message, missing transport component(s)");
        }

        try {
            Object amqpMessage = Optional.ofNullable(messageConverter)
                .map(converter -> (Object) converter.toMessage(message, properties))
                // return the original message if no converter is defined
                .orElse(message);

            if (message instanceof Message) {
                amqpTemplate.send(exchange, routingKey, (Message) amqpMessage);
            } else {
                amqpTemplate.convertAndSend(exchange, routingKey, amqpMessage);
            }

            return true;
        } catch (Throwable t) {
            if(t instanceof AmqpException) {
                throw t;
            }
            throw new AmqpException(t);
        }
    }

}
