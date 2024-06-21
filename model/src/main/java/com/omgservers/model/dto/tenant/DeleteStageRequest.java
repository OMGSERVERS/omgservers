package com.omgservers.model.dto.tenant;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteStageRequest implements ShardedRequest {

    @Valid
    Long tenantId;

    @Valid
    Long id;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
