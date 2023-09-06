package com.omgservers.dto.gateway;

import com.omgservers.model.assignedRuntime.AssignedRuntimeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRuntimeRequest {

    public static void validate(AssignRuntimeRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    URI server;
    Long connectionId;
    AssignedRuntimeModel assignedRuntime;
}
