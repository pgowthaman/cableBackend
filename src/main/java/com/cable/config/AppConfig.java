package com.cable.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * 
 * @author iamgo
 *
 */
@Configuration
@ComponentScan(basePackages = "com.cable", excludeFilters = {
		@ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION),
		@ComponentScan.Filter(value = Configuration.class, type = FilterType.ANNOTATION),

})
public class AppConfig {

}
