package com.omgservers.dto.versionModule;

import com.omgservers.dto.ShardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStageConfigShardRequest implements ShardRequest {

    static public void validate(GetStageConfigShardRequest request) {
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
