package com.omgservers.dto.versionModule;

import com.omgservers.dto.ShardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteVersionShardRequest implements ShardRequest {

    static public void validate(DeleteVersionShardRequest request) {
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
