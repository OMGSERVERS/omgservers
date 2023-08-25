package com.omgservers.dto.matchmakerModule;

import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMatchInternalRequest implements InternalRequest {

    static public void validate(DeleteMatchInternalRequest request) {
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
