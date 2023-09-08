package com.omgservers.dto.context;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.runtime.RuntimeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLuaInstanceForRuntimeEventsRequest {

    public static void validate(CreateLuaInstanceForRuntimeEventsRequest request) {
        if (request == null) {
            throw new ServerSideBadRequestException("request is null");
        }
        // TODO: validate fields
    }

    RuntimeModel runtime;
}
