package com.omgservers.dto.admin;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateTenantAdminRequest {

    static public void validate(CreateTenantAdminRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }
}
