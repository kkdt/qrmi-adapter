package qrmi.adapter.samples;

import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class StockListenerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(StockListenerApplication.class)
            .bannerMode(Banner.Mode.OFF)
            .logStartupInfo(true)
            .headless(true)
            .web(WebApplicationType.NONE)
            .run(args);
    }
}
