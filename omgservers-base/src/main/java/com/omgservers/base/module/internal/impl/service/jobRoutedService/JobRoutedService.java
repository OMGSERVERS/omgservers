package com.omgservers.base.module.internal.impl.service.jobRoutedService;

import com.omgservers.dto.internalModule.DeleteJobRoutedRequest;
import com.omgservers.dto.internalModule.DeleteJobRoutedResponse;
import com.omgservers.dto.internalModule.ScheduleJobRoutedRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedResponse;
import com.omgservers.dto.internalModule.UnscheduleJobRoutedRequest;
import io.smallrye.mutiny.Uni;

public interface JobRoutedService {
    Uni<SyncJobRoutedResponse> syncJob(SyncJobRoutedRequest request);

    Uni<DeleteJobRoutedResponse> deleteJob(DeleteJobRoutedRequest request);

    Uni<Void> scheduleJob(ScheduleJobRoutedRequest request);

    Uni<Void> unscheduleJob(UnscheduleJobRoutedRequest request);
}
