package com.omgservers.service.master.task.impl.operation;

import com.omgservers.service.operation.server.GetRestClientOperationImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetTaskMasterClientOperationImpl extends GetRestClientOperationImpl<TaskMasterClient>
        implements GetTaskMasterClientOperation {

    public GetTaskMasterClientOperationImpl() {
        super(TaskMasterClient.class);
    }
}
