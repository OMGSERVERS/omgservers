package com.omgservers.service.server.job;

import com.omgservers.service.server.job.dto.DeleteJobRequest;
import com.omgservers.service.server.job.dto.DeleteJobResponse;
import com.omgservers.service.server.job.dto.FindJobRequest;
import com.omgservers.service.server.job.dto.FindJobResponse;
import com.omgservers.service.server.job.dto.GetJobRequest;
import com.omgservers.service.server.job.dto.GetJobResponse;
import com.omgservers.service.server.job.dto.SyncJobRequest;
import com.omgservers.service.server.job.dto.SyncJobResponse;
import com.omgservers.service.server.job.dto.ViewJobsRequest;
import com.omgservers.service.server.job.dto.ViewJobsResponse;
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
