package com.omgservers.application.jobs;

import com.omgservers.module.internal.impl.service.jobShardedService.impl.JobTask;
import com.omgservers.application.module.matchmakerModule.MatchmakerModule;
import com.omgservers.dto.matchmakerModule.DoMatchmakingShardRequest;
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
        final var request = new DoMatchmakingShardRequest(entity);
        //TODO: handle response (proceed or not)
        return matchmakerModule.getMatchmakerInternalService().doMatchmaking(request)
                .replaceWith(true);
    }
}
