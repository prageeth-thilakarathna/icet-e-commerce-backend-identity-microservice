package edu.icet.demo.config;

import edu.icet.demo.filter.FirewallFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<FirewallFilter> firewallFilterRegistration() {
        FilterRegistrationBean<FirewallFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new FirewallFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
