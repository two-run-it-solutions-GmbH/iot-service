package com.example.iot.service.service.agent;

import com.example.iot.service.client.openremote.OpenRemoteClient;
import com.example.iot.service.configuration.openremote.OpenRemoteProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AssetService {
    private final OpenRemoteClient openRemoteClient;
    private final OpenRemoteProperties properties;

    public String createAsset(String realm, Map<String, Object> payload) {
        String targetRealm = (realm == null || realm.isBlank())
                ? properties.getAdminRealm() : realm; // istersen bu fallback’i kaldır
        return openRemoteClient.createAsset(targetRealm, payload);
    }
}
