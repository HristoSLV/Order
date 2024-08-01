package com.catJam.Order.bookClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class BookClientConfig {

    private static final String BOOK_SERVICE_CONNECTION = "https://http://localhost:8081/";

    @Bean
    BookClient bookClient() {
        RestClient client = RestClient.create(BOOK_SERVICE_CONNECTION);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(client))
                .build();
        return factory.createClient(BookClient.class);
    }
}
