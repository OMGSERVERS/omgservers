package com.omgservers.dto.tenant;

import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStageVersionIdShardedRequest implements ShardedRequest {

    public static void validate(GetStageVersionIdShardedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long tenantId;
    Long stageId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
