package de.thd.systemdesign.p2p;

import de.thd.systemdesign.p2p.config.NodeConfig;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class P2PApplicationConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    public NodeConfig node() {
        boolean master = System.getenv("ISMASTER") != null || System.getProperty("p2p.master.itsme") != null;
        String port = System.getProperty("server.port", "8080");
        NodeConfig cfg = new NodeConfig(master);
        cfg.setPort(Integer.parseInt(port));
        return cfg;
    }
}