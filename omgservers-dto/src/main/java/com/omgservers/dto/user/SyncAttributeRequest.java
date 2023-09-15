package com.omgservers.dto.user;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.attribute.AttributeModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncAttributeRequest implements ShardedRequest {

    @NotNull
    Long userId;

    @NotNull
    AttributeModel attribute;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
