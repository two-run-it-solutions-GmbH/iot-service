package com.example.iot.service.controller.realm;

import com.example.iot.service.model.request.realm.CreateRealmRequest;
import com.example.iot.service.service.realm.RealmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/or/realms")
@RequiredArgsConstructor
public class RealmController {
    private final RealmService realmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody CreateRealmRequest request) {
        return realmService.create(request);
    }
}
