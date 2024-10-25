package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handlerMatchmakerRequests;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;

public interface HandleMatchmakerRequestsOperation {
    void execute(MatchmakerStateDto matchmakerState,
                 MatchmakerChangeOfStateDto matchmakerChangeOfState,
                 TenantVersionConfigDto tenantVersionConfig);
}
