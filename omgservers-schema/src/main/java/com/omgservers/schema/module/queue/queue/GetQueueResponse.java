package com.omgservers.schema.module.queue.queue;

import com.omgservers.schema.model.queue.QueueModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetQueueResponse {

    QueueModel queue;
}
