package com.omgservers.application.module.userModule.impl.service.playerInternalService.request;

import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerInternalRequest implements InternalRequest {

    static public void validate(GetPlayerInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    Long stageId;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
