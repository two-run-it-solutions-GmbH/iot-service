package com.example.iot.service.model.request.agent;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMqttAgentRequest {
    private String  name;                 // "MQTT Agent - X" gibi
    private String  host;                 // broker host/IP
    private Integer port;                 // null ise tls? 8883:1883
    private boolean tls;                  // true => 8883
    private String  clientId;
    private String  username;
    private String  password;
    private String  lastWillTopic;
    private String  lastWillPayload;
    private Boolean lastWillRetain;
    private Integer publishQos;
    private Integer subscribeQos;
    private Boolean resumeSession;        // default: false
}