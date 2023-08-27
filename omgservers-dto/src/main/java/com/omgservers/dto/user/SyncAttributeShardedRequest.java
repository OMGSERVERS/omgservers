package com.omgservers.dto.user;

import com.omgservers.model.attribute.AttributeModel;
import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncAttributeShardedRequest implements ShardedRequest {

    static public void validate(SyncAttributeShardedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    AttributeModel attribute;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
