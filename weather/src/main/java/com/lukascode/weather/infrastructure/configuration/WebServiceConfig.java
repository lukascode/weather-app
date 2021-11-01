package com.lukascode.weather.infrastructure.configuration;

import com.lukascode.weather.api.WeatherEndpoint;
import com.lukascode.weather.ws.WeatherServiceWS;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {

    @Bean
    public ServletRegistrationBean<CXFServlet> cxfServlet() {
        return new ServletRegistrationBean<>(new CXFServlet(), "/ws/*");
    }

    @Bean
    public WeatherServiceWS weatherServiceWS() {
        return new WeatherEndpoint();
    }

    @Bean
    public Endpoint endpoint(Bus bus, WeatherServiceWS weatherService) {
        EndpointImpl endpoint = new EndpointImpl(bus, weatherService);
        endpoint.publish("/WeatherService");
        return endpoint;
    }

}
