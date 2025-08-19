package com.example.iot.service.configuration.feign.keycloak;

import feign.Request;
import org.springframework.context.annotation.Bean;

public class KeycloakClientFeignConfiguration {

    @Bean
    public Request.Options options() {
        return new Request.Options(30_000, 30_000);
    }

    @Bean
    public feign.codec.Encoder feignFormEncoder() {
        return new feign.form.spring.SpringFormEncoder();
    }

    @Bean
    public feign.Client feignClient() throws Exception {
        javax.net.ssl.TrustManager[] trustAll = new javax.net.ssl.TrustManager[]{
                new javax.net.ssl.X509TrustManager() {
                    public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String s) {
                    }

                    public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String s) {
                    }

                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[0];
                    }
                }
        };
        javax.net.ssl.SSLContext ctx = javax.net.ssl.SSLContext.getInstance("TLS");
        ctx.init(null, trustAll, new java.security.SecureRandom());
        return new feign.Client.Default(ctx.getSocketFactory(), (hostname, session) -> true);
    }

    @Bean
    public feign.Logger.Level feignLoggerLevel() {
        return feign.Logger.Level.FULL;
    }

    @Bean
    public feign.codec.ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            String body = "";
            try {
                if (response.body() != null) {
                    body = feign.Util.toString(response.body().asReader(java.nio.charset.StandardCharsets.UTF_8));
                }
            } catch (Exception ignored) {
            }
            org.slf4j.LoggerFactory.getLogger(getClass())
                    .error("Feign error -> {} {} body={}", methodKey, response.status(), body);

            return new feign.codec.ErrorDecoder.Default().decode(methodKey, response);
        };
    }
}
