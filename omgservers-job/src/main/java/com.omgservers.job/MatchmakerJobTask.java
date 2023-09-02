package com.omgservers.job;

import com.omgservers.dto.matchmaker.ExecuteMatchmakerShardedRequest;
import com.omgservers.model.job.JobType;
import com.omgservers.module.internal.impl.service.jobShardedService.impl.JobTask;
import com.omgservers.module.matchmaker.MatchmakerModule;
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
        final var request = new ExecuteMatchmakerShardedRequest(entity);
        //TODO: handle response (proceed or not)
        return matchmakerModule.getMatchmakerShardedService().executeMatchmaker(request)
                .replaceWith(true);
    }
}
