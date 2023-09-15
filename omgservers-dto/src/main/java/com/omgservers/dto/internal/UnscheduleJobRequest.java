package com.omgservers.dto.internal;

import com.omgservers.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnscheduleJobRequest implements ShardedRequest {


    @NotNull
    Long shardKey;

    @NotNull
    Long entity;

    @Override
    public String getRequestShardKey() {
        return shardKey.toString();
    }
}
