package com.omgservers.dto.adminModule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTenantAdminRequest {

    static public void validate(CreateTenantAdminRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    //TODO: remove ??
    String title;
}
