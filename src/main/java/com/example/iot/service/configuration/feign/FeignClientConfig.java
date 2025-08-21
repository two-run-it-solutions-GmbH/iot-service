package com.example.iot.service.configuration.feign;

import com.example.iot.service.service.auth.TokenService;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor(TokenService tokenService) {
        return tpl -> {
            String path = tpl.path(); // örn: "/company/assets"
            String[] parts = path.split("/");

            String realm = (parts.length > 1) ? parts[1] : null;
            if (realm == null || realm.isBlank()) {
                realm = "master"; // fallback
            }

            tpl.header("Authorization", "Bearer " + tokenService.getTokenForRealm(realm));
            tpl.header("Accept", "application/json");

        };
    }

    @Bean
    public feign.Client feignClient() throws Exception {
        var ctx = javax.net.ssl.SSLContext.getInstance("TLS");
        ctx.init(null, new javax.net.ssl.TrustManager[]{new javax.net.ssl.X509TrustManager() {
            public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String s) {
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String s) {
            }

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }}, new java.security.SecureRandom());
        return new feign.Client.Default(ctx.getSocketFactory(), (h, s) -> true);
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
