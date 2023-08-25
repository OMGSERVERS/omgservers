package com.omgservers.dto.internalModule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteServiceAccountHelpRequest {

    static public void validate(DeleteServiceAccountHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long id;
}
