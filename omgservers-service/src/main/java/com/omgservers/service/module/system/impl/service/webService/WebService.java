package com.omgservers.service.module.system.impl.service.webService;

import com.omgservers.model.dto.system.DeleteJobRequest;
import com.omgservers.model.dto.system.DeleteJobResponse;
import com.omgservers.model.dto.system.ScheduleJobRequest;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncJobRequest;
import com.omgservers.model.dto.system.SyncJobResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.UnscheduleJobRequest;
import com.omgservers.model.dto.system.ViewLogRequest;
import com.omgservers.model.dto.system.ViewLogsResponse;
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
