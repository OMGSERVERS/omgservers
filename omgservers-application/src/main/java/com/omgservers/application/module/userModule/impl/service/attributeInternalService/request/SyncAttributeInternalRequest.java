package com.omgservers.application.module.userModule.impl.service.attributeInternalService.request;

import com.omgservers.application.module.userModule.model.attribute.AttributeModel;
import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncAttributeInternalRequest implements InternalRequest {

    static public void validate(SyncAttributeInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UUID user;
    AttributeModel attribute;

    @Override
    public String getRequestShardKey() {
        return user.toString();
    }
}
