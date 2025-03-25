package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModeDto;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.component.MatchmakerMatcher;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CreateMatchmakerMatcherOperationImpl implements CreateMatchmakerMatcherOperation {

    @Override
    public MatchmakerMatcher execute(final TenantVersionModeDto modeConfig,
                                     final List<MatchmakerMatchResourceModel> matchmakerMatchResources,
                                     final List<MatchmakerMatchAssignmentModel> matchmakerMatchAssignments) {
        final var matchmakerMatcher = new MatchmakerMatcher(modeConfig);

        matchmakerMatchResources.forEach(matchmakerMatcher::addMatchmakerMatchResource);
        matchmakerMatchAssignments.forEach(matchmakerMatcher::addMatchmakerMatchAssignment);

        return matchmakerMatcher;
    }
}
