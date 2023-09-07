package com.omgservers.dto.tenant;

import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionRequest implements ShardedRequest {

    public static void validate(GetVersionRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long tenantId;
    Long id;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}