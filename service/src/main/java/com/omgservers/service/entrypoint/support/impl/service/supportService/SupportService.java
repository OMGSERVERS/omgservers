package com.omgservers.service.entrypoint.support.impl.service.supportService;

import com.omgservers.model.dto.support.CreateDeveloperSupportRequest;
import com.omgservers.model.dto.support.CreateDeveloperSupportResponse;
import com.omgservers.model.dto.support.CreateTenantSupportRequest;
import com.omgservers.model.dto.support.CreateTenantSupportResponse;
import com.omgservers.model.dto.support.CreateTokenSupportRequest;
import com.omgservers.model.dto.support.CreateTokenSupportResponse;
import com.omgservers.model.dto.support.DeleteTenantSupportRequest;
import com.omgservers.model.dto.support.DeleteTenantSupportResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface SupportService {

    Uni<CreateTokenSupportResponse> createToken(@Valid CreateTokenSupportRequest request);

    Uni<CreateTenantSupportResponse> createTenant(@Valid CreateTenantSupportRequest request);

    Uni<DeleteTenantSupportResponse> deleteTenant(@Valid DeleteTenantSupportRequest request);

    Uni<CreateDeveloperSupportResponse> createDeveloper(@Valid CreateDeveloperSupportRequest request);
}
