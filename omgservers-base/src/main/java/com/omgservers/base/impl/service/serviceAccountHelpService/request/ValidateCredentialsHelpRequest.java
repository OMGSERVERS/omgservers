package com.omgservers.base.impl.service.serviceAccountHelpService.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCredentialsHelpRequest {

    static public void validate(ValidateCredentialsHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    String username;
    @ToString.Exclude
    String password;
}
