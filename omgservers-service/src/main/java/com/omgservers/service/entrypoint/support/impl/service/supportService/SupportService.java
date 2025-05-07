package com.omgservers.service.entrypoint.support.impl.service.supportService;

import com.omgservers.schema.entrypoint.support.CreateDeveloperAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperAliasSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantAliasSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectAliasSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantStageAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStageAliasSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantStageSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStageSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantStageSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStageSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface SupportService {

    Uni<CreateTokenSupportResponse> execute(@Valid CreateTokenSupportRequest request);

    /*
    Tenant
     */

    Uni<CreateTenantSupportResponse> execute(@Valid CreateTenantSupportRequest request);

    Uni<CreateTenantAliasSupportResponse> execute(@Valid CreateTenantAliasSupportRequest request);

    Uni<DeleteTenantSupportResponse> execute(@Valid DeleteTenantSupportRequest request);

    /*
    Project
     */

    Uni<CreateTenantProjectSupportResponse> execute(@Valid CreateTenantProjectSupportRequest request);

    Uni<CreateTenantProjectAliasSupportResponse> execute(@Valid CreateTenantProjectAliasSupportRequest request);

    Uni<DeleteTenantProjectSupportResponse> execute(@Valid DeleteTenantProjectSupportRequest request);

    /*
    Stage
     */

    Uni<CreateTenantStageSupportResponse> execute(@Valid CreateTenantStageSupportRequest request);

    Uni<CreateTenantStageAliasSupportResponse> execute(@Valid CreateTenantStageAliasSupportRequest request);

    Uni<DeleteTenantStageSupportResponse> execute(@Valid DeleteTenantStageSupportRequest request);

    /*
    Developer
     */

    Uni<CreateDeveloperSupportResponse> execute(@Valid CreateDeveloperSupportRequest request);

    Uni<CreateDeveloperAliasSupportResponse> execute(@Valid CreateDeveloperAliasSupportRequest request);

    Uni<DeleteDeveloperSupportResponse> execute(@Valid DeleteDeveloperSupportRequest request);

    /*
    Permissions
     */

    Uni<CreateTenantPermissionsSupportResponse> execute(@Valid CreateTenantPermissionsSupportRequest request);

    Uni<DeleteTenantPermissionsSupportResponse> execute(@Valid DeleteTenantPermissionsSupportRequest request);

    Uni<CreateTenantProjectPermissionsSupportResponse> execute(
            @Valid CreateTenantProjectPermissionsSupportRequest request);

    Uni<DeleteTenantProjectPermissionsSupportResponse> execute(@Valid DeleteTenantProjectPermissionsSupportRequest request);

    Uni<CreateTenantStagePermissionsSupportResponse> execute(@Valid CreateTenantStagePermissionsSupportRequest request);

    Uni<DeleteTenantStagePermissionsSupportResponse> execute(@Valid DeleteTenantStagePermissionsSupportRequest request);
}
