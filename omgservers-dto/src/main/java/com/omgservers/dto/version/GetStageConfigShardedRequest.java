package com.omgservers.dto.version;

import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStageConfigShardedRequest implements ShardedRequest {

    static public void validate(GetStageConfigShardedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long versionId;

    @Override
    public String getRequestShardKey() {
        return versionId.toString();
    }
}
