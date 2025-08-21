package com.example.iot.service.converter;

import com.example.iot.service.model.response.agent.AssetResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AssetConverter {
    private final ObjectMapper mapper = new ObjectMapper();

    public AssetResponse convert(String json) {
        try {
            JsonNode n = mapper.readTree(json);
            AssetResponse r = new AssetResponse();
            r.setId(n.path("id").asText(null));
            r.setName(n.path("name").asText(null));
            r.setType(n.path("type").asText(null));
            if (n.has("attributes")) {
                @SuppressWarnings("unchecked")
                Map<String, Object> attrs = mapper.convertValue(n.get("attributes"), Map.class);
                r.setAttributes(attrs);
            }
            return r;
        } catch (Exception e) {
            throw new IllegalStateException("Asset parse failed: " + e.getMessage(), e);
        }
    }
}