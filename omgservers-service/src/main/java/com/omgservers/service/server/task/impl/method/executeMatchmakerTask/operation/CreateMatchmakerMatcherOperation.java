package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModeDto;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.component.MatchmakerMatcher;

import java.util.List;

public interface CreateMatchmakerMatcherOperation {
    MatchmakerMatcher execute(TenantVersionModeDto modeConfig,
                              List<MatchmakerMatchResourceModel> matchmakerMatchResources,
                              List<MatchmakerMatchAssignmentModel> matchmakerMatchAssignments);
}
