package com.example.iot.service.model.response.agent;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetResponse {
    private String id;
    private String name;
    private String type;
    private Map<String, Object> attributes;
}