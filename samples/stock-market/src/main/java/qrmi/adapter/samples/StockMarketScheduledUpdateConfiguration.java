package qrmi.adapter.samples;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import qrmi.adapter.samples.model.Stock;

@Configuration
@EnableScheduling
public class StockMarketScheduledUpdateConfiguration {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Value("${stockmarket.exchange}")
    private String exchange;

    @Bean
    public StockProducer stockProducer() {
        return new StockProducer(exchange, new RabbitTemplate(connectionFactory));
    }

    @Scheduled(initialDelay = 5000, fixedRate = 1000)
    public void randomUpdate() {
        final Random rand = new Random();
        final Supplier<Stock> random = () -> {
            UUID uuid = UUID.randomUUID();
            Stock stock = new Stock();
            stock.setIpoDate(new Date());
            stock.setLastUpdate(new Date());
            stock.setPrice(rand.nextDouble());
            return stock;
        };

        stockProducer().broadcast(random.get());
    }
}
