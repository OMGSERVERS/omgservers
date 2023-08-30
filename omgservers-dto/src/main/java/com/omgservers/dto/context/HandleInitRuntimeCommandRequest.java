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
public class HandleInitRuntimeCommandRequest {

    static public void validate(final HandleInitRuntimeCommandRequest request) {
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
}
