package com.omgservers.service.module.system.impl.service.webService;

import com.omgservers.model.dto.system.DeleteEntityRequest;
import com.omgservers.model.dto.system.DeleteEntityResponse;
import com.omgservers.model.dto.system.DeleteIndexRequest;
import com.omgservers.model.dto.system.DeleteIndexResponse;
import com.omgservers.model.dto.system.DeleteJobRequest;
import com.omgservers.model.dto.system.DeleteJobResponse;
import com.omgservers.model.dto.system.DeleteServiceAccountRequest;
import com.omgservers.model.dto.system.DeleteServiceAccountResponse;
import com.omgservers.model.dto.system.FindEntityRequest;
import com.omgservers.model.dto.system.FindEntityResponse;
import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.model.dto.system.FindJobRequest;
import com.omgservers.model.dto.system.FindJobResponse;
import com.omgservers.model.dto.system.FindServiceAccountRequest;
import com.omgservers.model.dto.system.FindServiceAccountResponse;
import com.omgservers.model.dto.system.GetIndexRequest;
import com.omgservers.model.dto.system.GetIndexResponse;
import com.omgservers.model.dto.system.GetJobRequest;
import com.omgservers.model.dto.system.GetJobResponse;
import com.omgservers.model.dto.system.GetServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountResponse;
import com.omgservers.model.dto.system.ScheduleJobRequest;
import com.omgservers.model.dto.system.SyncEntityRequest;
import com.omgservers.model.dto.system.SyncEntityResponse;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import com.omgservers.model.dto.system.SyncJobRequest;
import com.omgservers.model.dto.system.SyncJobResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.SyncServiceAccountResponse;
import com.omgservers.model.dto.system.UnscheduleJobRequest;
import com.omgservers.model.dto.system.ViewLogRequest;
import com.omgservers.model.dto.system.ViewLogsResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetIndexResponse> getIndex(GetIndexRequest request);

    Uni<FindIndexResponse> findIndex(FindIndexRequest request);

    Uni<SyncIndexResponse> syncIndex(SyncIndexRequest request);

    Uni<DeleteIndexResponse> deleteIndex(DeleteIndexRequest request);

    Uni<GetServiceAccountResponse> getServiceAccount(GetServiceAccountRequest request);

    Uni<FindServiceAccountResponse> findServiceAccount(FindServiceAccountRequest request);

    Uni<SyncServiceAccountResponse> syncServiceAccount(SyncServiceAccountRequest request);

    Uni<DeleteServiceAccountResponse> deleteServiceAccount(DeleteServiceAccountRequest request);

    Uni<GetJobResponse> getJob(GetJobRequest request);

    Uni<FindJobResponse> findJob(FindJobRequest request);

    Uni<SyncJobResponse> syncJob(SyncJobRequest request);

    Uni<DeleteJobResponse> deleteJob(DeleteJobRequest request);

    Uni<Void> scheduleJob(ScheduleJobRequest request);

    Uni<Void> unscheduleJob(UnscheduleJobRequest request);

    Uni<ViewLogsResponse> viewLogs(ViewLogRequest request);

    Uni<FindEntityResponse> findEntity(FindEntityRequest request);

    Uni<SyncEntityResponse> syncEntity(SyncEntityRequest request);

    Uni<DeleteEntityResponse> deleteEntity(DeleteEntityRequest request);
}
