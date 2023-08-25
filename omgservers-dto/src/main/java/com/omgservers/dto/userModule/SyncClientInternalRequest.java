package com.omgservers.dto.userModule;

import com.omgservers.model.client.ClientModel;
import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncClientInternalRequest implements InternalRequest {

    static public void validate(SyncClientInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    ClientModel client;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
