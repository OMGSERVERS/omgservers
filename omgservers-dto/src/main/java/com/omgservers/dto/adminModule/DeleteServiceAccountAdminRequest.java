package com.omgservers.dto.adminModule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteServiceAccountAdminRequest {

    static public void validate(DeleteServiceAccountAdminRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long id;
}
