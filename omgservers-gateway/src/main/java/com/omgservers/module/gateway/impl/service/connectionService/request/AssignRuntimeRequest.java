package com.omgservers.module.gateway.impl.service.connectionService.request;

import com.omgservers.model.assignedRuntime.AssignedRuntimeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRuntimeRequest {

    public static void validate(AssignRuntimeRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long connectionId;
    AssignedRuntimeModel assignedRuntime;
}
