package com.omgservers.dto.userModule;

import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteObjectInternalRequest implements InternalRequest {

    static public void validate(DeleteObjectInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    Long id;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
