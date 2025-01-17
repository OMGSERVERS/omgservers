package com.omgservers.schema.module.queue.queueRequest;

import com.omgservers.schema.model.queueRequest.QueueRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindQueueRequestResponse {

    QueueRequestModel queueRequest;
}
