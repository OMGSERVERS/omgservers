package com.omgservers.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteIndexRequest {

    static public void validate(DeleteIndexRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long id;
}