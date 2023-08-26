package com.omgservers.dto.versionModule;

import com.omgservers.dto.ShardRequest;
import com.omgservers.model.version.VersionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVersionShardRequest implements ShardRequest {

    static public void validate(CreateVersionShardRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    VersionModel version;

    @Override
    public String getRequestShardKey() {
        return version.getId().toString();
    }
}
