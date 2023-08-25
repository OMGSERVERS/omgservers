package com.omgservers.dto.adminModule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetIndexAdminRequest {

    static public void validate(GetIndexAdminRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    String name;
}
