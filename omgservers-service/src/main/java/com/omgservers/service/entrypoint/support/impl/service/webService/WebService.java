package com.omgservers.service.entrypoint.support.impl.service.webService;

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
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantStageAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStageAliasSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantStageSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStageSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantStageSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStageSupportResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenSupportResponse> execute(CreateTokenSupportRequest request);

    /*
    Tenant
     */

    Uni<CreateTenantSupportResponse> execute(CreateTenantSupportRequest request);

    Uni<CreateTenantAliasSupportResponse> execute(CreateTenantAliasSupportRequest request);

    Uni<DeleteTenantSupportResponse> execute(DeleteTenantSupportRequest request);

    /*
    Project
     */

    Uni<CreateTenantProjectSupportResponse> execute(CreateTenantProjectSupportRequest request);

    Uni<CreateTenantProjectAliasSupportResponse> execute(CreateTenantProjectAliasSupportRequest request);

    Uni<DeleteTenantProjectSupportResponse> execute(DeleteTenantProjectSupportRequest request);

    /*
    Stage
     */

    Uni<CreateTenantStageSupportResponse> execute(CreateTenantStageSupportRequest request);

    Uni<CreateTenantStageAliasSupportResponse> execute(CreateTenantStageAliasSupportRequest request);

    Uni<DeleteTenantStageSupportResponse> execute(DeleteTenantStageSupportRequest request);

    /*
    Developer
     */

    Uni<CreateDeveloperSupportResponse> execute(CreateDeveloperSupportRequest request);

    Uni<CreateDeveloperAliasSupportResponse> execute(CreateDeveloperAliasSupportRequest request);

    Uni<DeleteDeveloperSupportResponse> execute(DeleteDeveloperSupportRequest request);

    /*
    Permissions
     */

    Uni<CreateTenantPermissionsSupportResponse> execute(CreateTenantPermissionsSupportRequest request);

    Uni<DeleteTenantPermissionsSupportResponse> execute(DeleteTenantPermissionsSupportRequest request);

    Uni<CreateTenantProjectPermissionsSupportResponse> execute(CreateTenantProjectPermissionsSupportRequest request);

    Uni<DeleteTenantProjectPermissionsSupportResponse> execute(DeleteTenantProjectPermissionsSupportRequest request);

    Uni<CreateTenantStagePermissionsSupportResponse> execute(CreateTenantStagePermissionsSupportRequest request);

    Uni<DeleteTenantStagePermissionsSupportResponse> execute(DeleteTenantStagePermissionsSupportRequest request);
}
