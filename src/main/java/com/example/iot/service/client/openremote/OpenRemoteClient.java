package com.example.iot.service.client.openremote;

import com.example.iot.service.configuration.feign.FeignClientConfig;
import com.example.iot.service.model.request.realm.CreateRealmRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
        name = "openremoteClient",
        url = "${openremote.api-base-url}/api",
        configuration = FeignClientConfig.class
)
public interface OpenRemoteClient {

    @PostMapping(value = "/{adminRealm}/realm", consumes = MediaType.APPLICATION_JSON_VALUE)
    String createRealm(@PathVariable("adminRealm") String adminRealm,
                       @RequestBody CreateRealmRequest payload);

}
