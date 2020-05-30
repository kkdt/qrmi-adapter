package qrmi.adapter;

import java.util.Optional;
import org.springframework.amqp.core.MessageProperties;

/**
 * An adapter interface that pulls the current executing thread {@link AmqpTransport} to determine how to broadcast messages.
 *
 * @see AmqpTransport
 */
public interface AmqpProducer {

    /**
     * Publish a message <code>m</code> with the specified properties to RabbitMQ.
     *
     * @param m
     * @param properties
     */
    default void publish(Object m, MessageProperties properties) {
        if(canPublish(m)) {
            AmqpTransport amqpTransport = Optional.ofNullable(AmqpTransportContext.get())
                .orElseThrow(() -> new IllegalStateException("Cannot publish message due to missing transport"));
            amqpTransport.apply(m, properties);
        }
    }

    /**
     * Determine if a message should be published - by default, always true.
     *
     * @param o
     * @return true if the object should be published; false, otherwise.
     */
    default boolean canPublish(Object o) {
        return true;
    }
}
