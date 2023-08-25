package com.omgservers.dto.userModule;

import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetObjectInternalRequest implements InternalRequest {

    static public void validate(GetObjectInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    Long playerId;
    String name;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
