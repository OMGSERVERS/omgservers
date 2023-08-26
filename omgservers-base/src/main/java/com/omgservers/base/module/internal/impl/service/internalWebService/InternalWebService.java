package com.omgservers.base.module.internal.impl.service.internalWebService;

import com.omgservers.dto.internalModule.DeleteJobRoutedRequest;
import com.omgservers.dto.internalModule.DeleteJobRoutedResponse;
import com.omgservers.dto.internalModule.FireEventRoutedRequest;
import com.omgservers.dto.internalModule.FireEventRoutedResponse;
import com.omgservers.dto.internalModule.ScheduleJobRoutedRequest;
import com.omgservers.dto.internalModule.SyncIndexRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedResponse;
import com.omgservers.dto.internalModule.SyncServiceAccountRequest;
import com.omgservers.dto.internalModule.UnscheduleJobRoutedRequest;
import com.omgservers.dto.internalModule.ViewLogRequest;
import com.omgservers.dto.internalModule.ViewLogsResponse;
import io.smallrye.mutiny.Uni;

public interface InternalWebService {
    Uni<Void> syncIndex(SyncIndexRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountRequest request);

    Uni<FireEventRoutedResponse> fireEvent(FireEventRoutedRequest request);

    Uni<SyncJobRoutedResponse> syncJob(SyncJobRoutedRequest request);

    Uni<DeleteJobRoutedResponse> deleteJob(DeleteJobRoutedRequest request);

    Uni<Void> scheduleJob(ScheduleJobRoutedRequest request);

    Uni<Void> unscheduleJob(UnscheduleJobRoutedRequest request);

    Uni<ViewLogsResponse> viewLogs(ViewLogRequest request);
}
