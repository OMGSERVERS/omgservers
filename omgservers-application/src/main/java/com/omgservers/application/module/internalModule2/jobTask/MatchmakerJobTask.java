package com.omgservers.application.module.internalModule2.jobTask;

import com.omgservers.base.module.internal.impl.service.jobRoutedService.impl.JobTask;
import com.omgservers.application.module.matchmakerModule.MatchmakerModule;
import com.omgservers.dto.matchmakerModule.DoMatchmakingRoutedRequest;
import com.omgservers.model.job.JobType;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerJobTask implements JobTask {

    final MatchmakerModule matchmakerModule;

    @Override
    public JobType getJobType() {
        return JobType.MATCHMAKER;
    }

    @Override
    public Uni<Boolean> executeTask(Long shardKey, Long entity) {
        final var request = new DoMatchmakingRoutedRequest(entity);
        //TODO: handle response (proceed or not)
        return matchmakerModule.getMatchmakerInternalService().doMatchmaking(request)
                .replaceWith(true);
    }
}
