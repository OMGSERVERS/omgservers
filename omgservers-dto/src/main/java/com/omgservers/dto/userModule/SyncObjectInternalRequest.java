package com.omgservers.dto.userModule;

import com.omgservers.model.object.ObjectModel;
import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncObjectInternalRequest implements InternalRequest {

    static public void validate(SyncObjectInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    ObjectModel object;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
