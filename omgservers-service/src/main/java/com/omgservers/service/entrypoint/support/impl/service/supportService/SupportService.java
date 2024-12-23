package com.omgservers.service.entrypoint.support.impl.service.supportService;

import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportResponse;
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
import com.omgservers.schema.entrypoint.support.DeleteProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteProjectPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface SupportService {

    Uni<CreateTokenSupportResponse> execute(@Valid CreateTokenSupportRequest request);

    Uni<CreateTenantSupportResponse> execute(@Valid CreateTenantSupportRequest request);

    Uni<DeleteTenantSupportResponse> execute(@Valid DeleteTenantSupportRequest request);

    Uni<CreateTenantProjectSupportResponse> execute(@Valid CreateTenantProjectSupportRequest request);

    Uni<DeleteTenantProjectSupportResponse> execute(@Valid DeleteTenantProjectSupportRequest request);

    Uni<CreateDeveloperSupportResponse> execute(@Valid CreateDeveloperSupportRequest request);

    Uni<DeleteDeveloperSupportResponse> execute(@Valid DeleteDeveloperSupportRequest request);

    Uni<CreateTenantPermissionsSupportResponse> execute(@Valid CreateTenantPermissionsSupportRequest request);

    Uni<DeleteTenantPermissionsSupportResponse> execute(@Valid DeleteTenantPermissionsSupportRequest request);

    Uni<CreateTenantProjectPermissionsSupportResponse> execute(
            @Valid CreateTenantProjectPermissionsSupportRequest request);

    Uni<DeleteProjectPermissionsSupportResponse> execute(@Valid DeleteProjectPermissionsSupportRequest request);

    Uni<CreateTenantStagePermissionsSupportResponse> execute(@Valid CreateTenantStagePermissionsSupportRequest request);

    Uni<DeleteTenantStagePermissionsSupportResponse> execute(@Valid DeleteTenantStagePermissionsSupportRequest request);
}
