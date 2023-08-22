package com.omgservers.application.module.userModule.impl.service.attributeInternalService.request;

import com.omgservers.application.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerAttributesInternalRequest implements InternalRequest {

    static public void validate(GetPlayerAttributesInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    Long playerId;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
