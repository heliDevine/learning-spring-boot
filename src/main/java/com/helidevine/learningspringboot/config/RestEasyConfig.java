package com.helidevine.learningspringboot.config;

import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Configuration
@ApplicationPath("/")
public class RestEasyConfig extends Application {
}
