package com.omgservers.dto.admin;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CollectLogsAdminRequest {

    static public void validate(CollectLogsAdminRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    // TODO: add filters, sort
}
