package com.helidevine.learningspringboot.clentproxy;


import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class ClientProxyConfig {

    @Value("${users.api.url.v1}")
    private String usersEndPointUrl;

    @Bean
    public UserResourceV1 getUserResourceV1() {

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(usersEndPointUrl);
        UserResourceV1 proxy = target.proxy(UserResourceV1.class);
        return proxy;
    }
}
