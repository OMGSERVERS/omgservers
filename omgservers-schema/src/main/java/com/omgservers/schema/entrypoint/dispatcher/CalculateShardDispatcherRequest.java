package com.omgservers.schema.entrypoint.dispatcher;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculateShardDispatcherRequest {

    @NotNull
    String shardKey;
}
