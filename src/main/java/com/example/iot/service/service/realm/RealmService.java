package com.example.iot.service.service.realm;

import com.example.iot.service.client.openremote.OpenRemoteClient;
import com.example.iot.service.model.request.realm.CreateRealmRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RealmService {
    private final OpenRemoteClient openRemoteClient;

    @Value("${openremote.admin-realm}") // master
    private String adminRealm;

    public String create(CreateRealmRequest createRealmRequest) {
        if (createRealmRequest.getEnabled() == null) createRealmRequest.setEnabled(true);
        return openRemoteClient.createRealm(adminRealm, createRealmRequest);
    }
}