package com.omgservers.module.system.impl.service.webService;

import com.omgservers.model.dto.internal.DeleteJobRequest;
import com.omgservers.model.dto.internal.DeleteJobResponse;
import com.omgservers.model.dto.internal.ScheduleJobRequest;
import com.omgservers.model.dto.internal.SyncIndexRequest;
import com.omgservers.model.dto.internal.SyncJobRequest;
import com.omgservers.model.dto.internal.SyncJobResponse;
import com.omgservers.model.dto.internal.SyncServiceAccountRequest;
import com.omgservers.model.dto.internal.UnscheduleJobRequest;
import com.omgservers.model.dto.internal.ViewLogRequest;
import com.omgservers.model.dto.internal.ViewLogsResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {
    Uni<Void> syncIndex(SyncIndexRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountRequest request);

    Uni<SyncJobResponse> syncJob(SyncJobRequest request);

    Uni<DeleteJobResponse> deleteJob(DeleteJobRequest request);

    Uni<Void> scheduleJob(ScheduleJobRequest request);

    Uni<Void> unscheduleJob(UnscheduleJobRequest request);

    Uni<ViewLogsResponse> viewLogs(ViewLogRequest request);
}
