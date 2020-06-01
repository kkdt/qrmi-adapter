package qrmi.adapter.samples;

import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class StockMarketApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(StockMarketApplication.class)
            .bannerMode(Banner.Mode.OFF)
            .logStartupInfo(true)
            .headless(true)
            .web(WebApplicationType.NONE)
            .run(args);
    }
}
