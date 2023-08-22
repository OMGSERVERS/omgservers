package com.omgservers.application.module.userModule.impl.service.attributeInternalService.request;

import com.omgservers.application.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAttributeInternalRequest implements InternalRequest {

    static public void validate(GetAttributeInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    Long playerId;
    String name;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
