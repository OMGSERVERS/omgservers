package com.omgservers.model.dto.tenant;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectStageVersionRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotNull
    Strategy strategy;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }

    public enum Strategy {
        LATEST,
    }
}
