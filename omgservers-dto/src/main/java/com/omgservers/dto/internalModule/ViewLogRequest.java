package com.omgservers.dto.internalModule;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ViewLogRequest {

    static public void validate(ViewLogRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }
}
