package com.omgservers.service.module.system.impl.service.webService;

import com.omgservers.model.dto.system.DeleteIndexRequest;
import com.omgservers.model.dto.system.DeleteIndexResponse;
import com.omgservers.model.dto.system.DeleteServiceAccountRequest;
import com.omgservers.model.dto.system.DeleteServiceAccountResponse;
import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.model.dto.system.FindServiceAccountRequest;
import com.omgservers.model.dto.system.FindServiceAccountResponse;
import com.omgservers.model.dto.system.GetIndexRequest;
import com.omgservers.model.dto.system.GetIndexResponse;
import com.omgservers.model.dto.system.GetServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountResponse;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.SyncServiceAccountResponse;
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
}
