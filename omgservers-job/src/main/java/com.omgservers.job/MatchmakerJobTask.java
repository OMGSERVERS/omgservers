package com.omgservers.job;

import com.omgservers.dto.matchmaker.ExecuteMatchmakerRequest;
import com.omgservers.model.job.JobTypeEnum;
import com.omgservers.module.system.impl.service.jobService.impl.JobTask;
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
    public JobTypeEnum getJobType() {
        return JobTypeEnum.MATCHMAKER;
    }

    @Override
    public Uni<Boolean> executeTask(Long shardKey, Long entityId) {
        final var request = new ExecuteMatchmakerRequest(entityId);
        //TODO: handle response (proceed or not)
        return matchmakerModule.getMatchmakerService().executeMatchmaker(request)
                .replaceWith(true);
    }
}
