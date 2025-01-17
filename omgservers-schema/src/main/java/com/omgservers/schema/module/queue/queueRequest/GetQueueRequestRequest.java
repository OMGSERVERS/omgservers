package com.omgservers.schema.module.queue.queueRequest;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetQueueRequestRequest implements ShardedRequest {

    @NotNull
    Long queueId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return queueId.toString();
    }
}
