package com.omgservers.dto.tenantModule;

import com.omgservers.model.stage.StageModel;
import com.omgservers.dto.RoutedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncStageRoutedRequest implements RoutedRequest {

    static public void validate(SyncStageRoutedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long tenantId;
    StageModel stage;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
