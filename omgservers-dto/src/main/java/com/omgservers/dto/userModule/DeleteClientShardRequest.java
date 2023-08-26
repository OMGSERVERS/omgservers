package com.omgservers.dto.userModule;

import com.omgservers.dto.ShardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteClientShardRequest implements ShardRequest {

    static public void validate(DeleteClientShardRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long userId;
    Long clientId;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
