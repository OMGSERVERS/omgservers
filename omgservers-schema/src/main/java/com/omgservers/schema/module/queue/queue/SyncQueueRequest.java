package com.omgservers.schema.module.queue.queue;

import com.omgservers.schema.model.queue.QueueModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncQueueRequest implements ShardedRequest {

    @NotNull
    QueueModel queue;

    @Override
    public String getRequestShardKey() {
        return queue.getId().toString();
    }
}
