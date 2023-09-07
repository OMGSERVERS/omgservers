package com.omgservers.dto.tenant;

import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionBytecodeRequest implements ShardedRequest {

    public static void validate(GetVersionBytecodeRequest request) {
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
