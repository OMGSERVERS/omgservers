package com.omgservers.dto.internalModule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetIndexRequest {

    static public void validate(GetIndexRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    String name;
}
