package com.omgservers.application.module.userModule.impl.service.clientInternalService.request;

import com.omgservers.application.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetClientInternalRequest implements InternalRequest {

    static public void validate(GetClientInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    Long clientId;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
