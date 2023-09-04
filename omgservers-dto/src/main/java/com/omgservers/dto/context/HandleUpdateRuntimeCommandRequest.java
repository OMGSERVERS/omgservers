package com.omgservers.dto.context;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HandleUpdateRuntimeCommandRequest {

    public static void validate(final HandleUpdateRuntimeCommandRequest request) {
        if (request == null) {
            throw new ServerSideBadRequestException("request is null");
        }
        // TODO: validate fields
    }

    Long tenantId;
    Long stageId;
    Long versionId;
    Long matchmakerId;
    Long matchId;
    Long runtimeId;
    Long step;
}
