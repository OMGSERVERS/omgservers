package com.omgservers.module.internal.impl.service.internalWebService;

import com.omgservers.dto.internal.DeleteJobShardedRequest;
import com.omgservers.dto.internal.DeleteJobShardedResponse;
import com.omgservers.dto.internal.FireEventShardedRequest;
import com.omgservers.dto.internal.FireEventShardedResponse;
import com.omgservers.dto.internal.ScheduleJobShardedRequest;
import com.omgservers.dto.internal.SyncIndexRequest;
import com.omgservers.dto.internal.SyncJobShardedRequest;
import com.omgservers.dto.internal.SyncJobShardedResponse;
import com.omgservers.dto.internal.SyncServiceAccountRequest;
import com.omgservers.dto.internal.UnscheduleJobShardedRequest;
import com.omgservers.dto.internal.ViewLogRequest;
import com.omgservers.dto.internal.ViewLogsResponse;
import io.smallrye.mutiny.Uni;

public interface InternalWebService {
    Uni<Void> syncIndex(SyncIndexRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountRequest request);

    Uni<FireEventShardedResponse> fireEvent(FireEventShardedRequest request);

    Uni<SyncJobShardedResponse> syncJob(SyncJobShardedRequest request);

    Uni<DeleteJobShardedResponse> deleteJob(DeleteJobShardedRequest request);

    Uni<Void> scheduleJob(ScheduleJobShardedRequest request);

    Uni<Void> unscheduleJob(UnscheduleJobShardedRequest request);

    Uni<ViewLogsResponse> viewLogs(ViewLogRequest request);
}
