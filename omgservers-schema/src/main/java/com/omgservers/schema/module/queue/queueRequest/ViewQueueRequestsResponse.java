package com.omgservers.schema.module.queue.queueRequest;

import com.omgservers.schema.model.queueRequest.QueueRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewQueueRequestsResponse {

    List<QueueRequestModel> queueRequests;
}
