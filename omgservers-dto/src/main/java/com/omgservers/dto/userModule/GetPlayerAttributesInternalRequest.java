package com.omgservers.dto.userModule;

import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerAttributesInternalRequest implements InternalRequest {

    static public void validate(GetPlayerAttributesInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    Long playerId;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
