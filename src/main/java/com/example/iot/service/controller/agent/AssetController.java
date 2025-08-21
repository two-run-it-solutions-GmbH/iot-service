package com.example.iot.service.controller.agent;

import com.example.iot.service.service.agent.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/or")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping("/{realm}/asset")
    @ResponseStatus(HttpStatus.CREATED)
    public String createAssetForRealm(@PathVariable String realm,
                                      @RequestBody Map<String, Object> payload) {
        return assetService.createAsset(realm, payload);
    }
}
