package com.example.iot.service.model.request.realm;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateRealmRequest {
    private String name;
    private String displayName;
    private Boolean enabled;
    private Boolean resetPasswordAllowed;
    private Boolean duplicateEmailsAllowed;
    private Boolean rememberMe;
    private Boolean registrationAllowed;
    private Boolean registrationEmailAsUsername;
    private Boolean verifyEmail;
    private Boolean loginWithEmail;
    private String loginTheme;
    private String accountTheme;
    private String adminTheme;
    private String emailTheme;
    private Integer accessTokenLifespan;
}