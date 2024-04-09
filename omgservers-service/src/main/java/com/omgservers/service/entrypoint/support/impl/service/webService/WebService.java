package com.omgservers.service.entrypoint.support.impl.service.webService;

import com.omgservers.model.dto.support.CreateDeveloperSupportRequest;
import com.omgservers.model.dto.support.CreateDeveloperSupportResponse;
import com.omgservers.model.dto.support.CreateDefaultPoolServerSupportRequest;
import com.omgservers.model.dto.support.CreateDefaultPoolServerSupportResponse;
import com.omgservers.model.dto.support.CreateTenantSupportRequest;
import com.omgservers.model.dto.support.CreateTenantSupportResponse;
import com.omgservers.model.dto.support.DeleteTenantSupportRequest;
import com.omgservers.model.dto.support.DeleteTenantSupportResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateDefaultPoolServerSupportResponse> createDefaultPoolServer(CreateDefaultPoolServerSupportRequest request);

    Uni<CreateTenantSupportResponse> createTenant(CreateTenantSupportRequest request);

    Uni<DeleteTenantSupportResponse> deleteTenant(DeleteTenantSupportRequest request);

    Uni<CreateDeveloperSupportResponse> createDeveloper(CreateDeveloperSupportRequest request);
}
