package com.catJam.Order.bookClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class BookClientConfig {

    @Value("${book.service.url}")
    private String bookServiceURL;

    @Bean
    BookClient bookClient() {
        RestClient client = RestClient.create(bookServiceURL);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(client))
                .build();
        return factory.createClient(BookClient.class);
    }
}
