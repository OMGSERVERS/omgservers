package com.omgservers.dto.tenant;

import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionConfigRequest implements ShardedRequest {

    public static void validate(GetVersionConfigRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long tenantId;
    Long versionId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
