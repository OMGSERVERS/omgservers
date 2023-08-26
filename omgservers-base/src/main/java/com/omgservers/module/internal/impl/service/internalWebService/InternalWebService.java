package com.omgservers.module.internal.impl.service.internalWebService;

import com.omgservers.dto.internalModule.DeleteJobShardRequest;
import com.omgservers.dto.internalModule.DeleteJobShardedResponse;
import com.omgservers.dto.internalModule.FireEventShardRequest;
import com.omgservers.dto.internalModule.FireEventShardedResponse;
import com.omgservers.dto.internalModule.ScheduleJobShardRequest;
import com.omgservers.dto.internalModule.SyncIndexRequest;
import com.omgservers.dto.internalModule.SyncJobShardRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedResponse;
import com.omgservers.dto.internalModule.SyncServiceAccountRequest;
import com.omgservers.dto.internalModule.UnscheduleJobShardRequest;
import com.omgservers.dto.internalModule.ViewLogRequest;
import com.omgservers.dto.internalModule.ViewLogsResponse;
import io.smallrye.mutiny.Uni;

public interface InternalWebService {
    Uni<Void> syncIndex(SyncIndexRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountRequest request);

    Uni<FireEventShardedResponse> fireEvent(FireEventShardRequest request);

    Uni<SyncJobRoutedResponse> syncJob(SyncJobShardRequest request);

    Uni<DeleteJobShardedResponse> deleteJob(DeleteJobShardRequest request);

    Uni<Void> scheduleJob(ScheduleJobShardRequest request);

    Uni<Void> unscheduleJob(UnscheduleJobShardRequest request);

    Uni<ViewLogsResponse> viewLogs(ViewLogRequest request);
}
