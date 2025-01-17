package com.omgservers.schema.module.queue.queueRequest;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteQueueRequestRequest implements ShardedRequest {

    @Valid
    Long queueId;

    @Valid
    Long id;

    @Override
    public String getRequestShardKey() {
        return queueId.toString();
    }
}
