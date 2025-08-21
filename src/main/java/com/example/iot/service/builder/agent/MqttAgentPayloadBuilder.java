package com.example.iot.service.builder.agent;

import com.example.iot.service.model.request.agent.CreateMqttAgentRequest;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class MqttAgentPayloadBuilder {

    public Map<String, Object> build(CreateMqttAgentRequest req) {
        int port = (req.getPort() != null) ? req.getPort() : (req.isTls() ? 8883 : 1883);

        Map<String, Object> attrs = new LinkedHashMap<>();
        attrs.put("agentDisabled", attr(false));
        attrs.put("host", attr(req.getHost()));
        attrs.put("hostname", attr(req.getHost())); // bazı sürümler hostname okuyor
        attrs.put("port", attr(port));
        attrs.put("secure", attr(req.isTls()));
        attrs.put("ssl", attr(req.isTls()));
        attrs.put("clientId", attr(req.getClientId()));

        if (notBlank(req.getUsername())) attrs.put("username", attr(req.getUsername()));
        if (req.getPassword() != null) attrs.put("password", attr(req.getPassword()));

        if (notBlank(req.getUsername()) || req.getPassword() != null) {
            Map<String, Object> up = new LinkedHashMap<>();
            if (notBlank(req.getUsername())) up.put("username", req.getUsername());
            if (req.getPassword() != null) up.put("password", req.getPassword());
            attrs.put("usernamePassword", attr(up)); // bazı kurulumlarda bu okunuyor
        }

        if (req.getPublishQos() != null) attrs.put("publishQos", attr(req.getPublishQos()));
        if (req.getSubscribeQos() != null) attrs.put("subscribeQos", attr(req.getSubscribeQos()));
        if (req.getResumeSession() != null) attrs.put("resumeSession", attr(req.getResumeSession()));
        if (req.getLastWillTopic() != null) attrs.put("lastWillTopic", attr(req.getLastWillTopic()));
        if (req.getLastWillPayload() != null) attrs.put("lastWillPayload", attr(req.getLastWillPayload()));
        if (req.getLastWillRetain() != null) attrs.put("lastWillRetain", attr(req.getLastWillRetain()));

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("name", req.getName());
        payload.put("type", "agent-mqtt");
        payload.put("attributes", attrs);
        return payload;
    }

    private static Map<String, Object> attr(Object v) {
        return Map.of("value", v);
    }

    private static boolean notBlank(String s) {
        return s != null && !s.isBlank();
    }
}
