package com.omgservers.application.module.adminModule.impl.service.adminHelpService.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTenantHelpRequest {

    static public void validate(CreateTenantHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    //TODO: remove ??
    String title;
}
