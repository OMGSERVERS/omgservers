package com.omgservers.dto.matchmakerModule;

import com.omgservers.dto.ShardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMatchShardRequest implements ShardRequest {

    static public void validate(DeleteMatchShardRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long matchmakerId;
    Long id;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}
