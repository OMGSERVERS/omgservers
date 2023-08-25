package com.omgservers.dto.userModule;

import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAttributeInternalRequest implements InternalRequest {

    static public void validate(DeleteAttributeInternalRequest request) {
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
