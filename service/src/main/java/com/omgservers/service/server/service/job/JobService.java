package com.omgservers.service.server.service.job;

import com.omgservers.schema.service.system.job.DeleteJobRequest;
import com.omgservers.schema.service.system.job.DeleteJobResponse;
import com.omgservers.schema.service.system.job.FindJobRequest;
import com.omgservers.schema.service.system.job.FindJobResponse;
import com.omgservers.schema.service.system.job.GetJobRequest;
import com.omgservers.schema.service.system.job.GetJobResponse;
import com.omgservers.schema.service.system.job.SyncJobRequest;
import com.omgservers.schema.service.system.job.SyncJobResponse;
import com.omgservers.schema.service.system.job.ViewJobsRequest;
import com.omgservers.schema.service.system.job.ViewJobsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface JobService {

    Uni<GetJobResponse> getJob(@Valid GetJobRequest request);

    Uni<FindJobResponse> findJob(@Valid FindJobRequest request);

    Uni<ViewJobsResponse> viewJobs(@Valid ViewJobsRequest request);

    Uni<SyncJobResponse> syncJob(@Valid SyncJobRequest request);

    Uni<SyncJobResponse> syncJobWithIdempotency(@Valid SyncJobRequest request);

    Uni<DeleteJobResponse> deleteJob(@Valid DeleteJobRequest request);
}
