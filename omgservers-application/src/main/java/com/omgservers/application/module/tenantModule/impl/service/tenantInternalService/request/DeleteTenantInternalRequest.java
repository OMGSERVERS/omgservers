package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request;

import com.omgservers.application.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTenantInternalRequest implements InternalRequest {

    static public void validate(DeleteTenantInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long id;

    @Override
    public String getRequestShardKey() {
        return id.toString();
    }
}
