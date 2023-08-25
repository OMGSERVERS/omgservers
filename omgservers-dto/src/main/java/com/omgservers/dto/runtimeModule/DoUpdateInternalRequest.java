package com.omgservers.dto.runtimeModule;

import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoUpdateInternalRequest implements InternalRequest {

    static public void validate(DoUpdateInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long runtimeId;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
