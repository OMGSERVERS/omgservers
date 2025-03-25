package com.omgservers.schema.module.client.clientRuntimeRef;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewClientRuntimeRefsRequest implements ShardedRequest {

    @NotNull
    Long clientId;

    @Override
    public String getRequestShardKey() {
        return clientId.toString();
    }
}
