package com.example.iot.service.configuration.feign.keycloak;

import feign.Client;
import feign.Request;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class KeycloakFeignConfig {

    @Value("${http.insecure-trust:false}")
    private boolean insecure;

    @Bean
    public Request.Options feignOptions() {
        return new Request.Options(30_000, 30_000);
    }

    @Bean
    public Encoder formEncoder() {
        return new SpringFormEncoder(); // Content-Type: application/x-www-form-urlencoded
    }

    @Bean
    public Client keycloakFeignClient() throws Exception {
        if (!insecure) return new Client.Default(null, null);
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] xcs, String s) {
            }

            public void checkServerTrusted(X509Certificate[] xcs, String s) {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new SecureRandom());
        return new Client.Default(ctx.getSocketFactory(), (h, s) -> true);
    }
}
