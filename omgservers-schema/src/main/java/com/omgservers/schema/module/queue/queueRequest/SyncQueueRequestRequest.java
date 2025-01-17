package com.omgservers.schema.module.queue.queueRequest;

import com.omgservers.schema.model.queueRequest.QueueRequestModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncQueueRequestRequest implements ShardedRequest {

    @NotNull
    QueueRequestModel queueRequest;

    @Override
    public String getRequestShardKey() {
        return queueRequest.getQueueId().toString();
    }
}
