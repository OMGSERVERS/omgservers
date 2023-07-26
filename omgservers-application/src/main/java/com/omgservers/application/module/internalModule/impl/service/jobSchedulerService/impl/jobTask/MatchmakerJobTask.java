package com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.impl.jobTask;

import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.impl.JobTask;
import com.omgservers.application.module.internalModule.model.job.JobType;
import com.omgservers.application.module.matchmakerModule.MatchmakerModule;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.DoMatchmakingInternalRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

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
        final var request = new DoMatchmakingInternalRequest(entity);
        //TODO: handle response (proceed or not)
        return matchmakerModule.getMatchmakerInternalService().doMatchmaking(request)
                .replaceWith(true);
    }
}
