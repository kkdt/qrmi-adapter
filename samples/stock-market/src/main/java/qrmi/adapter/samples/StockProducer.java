package qrmi.adapter.samples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import qrmi.adapter.samples.model.Stock;

public class StockProducer {
    private static final Logger logger = LoggerFactory.getLogger(StockProducer.class);

    private final AmqpTemplate amqpTemplate;
    private final String exchange;

    public StockProducer(String exchange, AmqpTemplate amqpTemplate) {
        this.exchange = exchange;
        this.amqpTemplate = amqpTemplate;
    }

    public void broadcast(Stock stock) {
        logger.info("Broadcasting stock to exchange {}: {}", exchange, stock);
        amqpTemplate.convertAndSend(exchange, null, stock);
    }
}
