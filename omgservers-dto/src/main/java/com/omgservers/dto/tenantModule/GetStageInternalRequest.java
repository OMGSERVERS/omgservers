package com.omgservers.dto.tenantModule;

import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStageInternalRequest implements InternalRequest {

    static public void validate(GetStageInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long tenantId;
    Long id;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
