package com.omgservers.dto.userModule;

import com.omgservers.model.attribute.AttributeModel;
import com.omgservers.dto.ShardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncAttributeShardRequest implements ShardRequest {

    static public void validate(SyncAttributeShardRequest request) {
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
