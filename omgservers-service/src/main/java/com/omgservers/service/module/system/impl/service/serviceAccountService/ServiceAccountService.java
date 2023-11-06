package com.omgservers.service.module.system.impl.service.serviceAccountService;

import com.omgservers.model.dto.system.DeleteServiceAccountRequest;
import com.omgservers.model.dto.system.DeleteServiceAccountResponse;
import com.omgservers.model.dto.system.FindServiceAccountRequest;
import com.omgservers.model.dto.system.FindServiceAccountResponse;
import com.omgservers.model.dto.system.GetServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.SyncServiceAccountResponse;
import com.omgservers.model.dto.system.ValidateCredentialsRequest;
import com.omgservers.model.dto.system.ValidateCredentialsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ServiceAccountService {

    Uni<GetServiceAccountResponse> getServiceAccount(@Valid GetServiceAccountRequest request);

    Uni<FindServiceAccountResponse> findServiceAccount(@Valid FindServiceAccountRequest request);

    Uni<SyncServiceAccountResponse> syncServiceAccount(@Valid SyncServiceAccountRequest request);

    Uni<DeleteServiceAccountResponse> deleteServiceAccount(@Valid DeleteServiceAccountRequest request);

    Uni<ValidateCredentialsResponse> validateCredentials(@Valid ValidateCredentialsRequest request);
}
