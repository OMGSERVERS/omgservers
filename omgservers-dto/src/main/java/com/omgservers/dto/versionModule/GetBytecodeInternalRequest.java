package com.omgservers.dto.versionModule;

import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBytecodeInternalRequest implements InternalRequest {

    static public void validate(GetBytecodeInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long versionId;

    @Override
    public String getRequestShardKey() {
        return versionId.toString();
    }
}
