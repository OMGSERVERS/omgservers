package com.omgservers.service.module.worker.impl.service.workerService.impl.method.getRuntimeState;

import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.model.dto.worker.GetRuntimeStateWorkerRequest;
import com.omgservers.model.dto.worker.GetRuntimeStateWorkerResponse;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.module.worker.impl.service.workerService.impl.operation.checkRuntimePermission.CheckRuntimePermissionOperation;
import com.omgservers.service.module.worker.impl.service.workerService.impl.operation.collectPlayers.CollectPlayersOperation;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetRuntimeStateMethodImpl implements GetRuntimeStateMethod {

    final RuntimeModule runtimeModule;
    final UserModule userModule;

    final CheckRuntimePermissionOperation checkRuntimePermissionOperation;
    final CollectPlayersOperation collectPlayersOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<GetRuntimeStateWorkerResponse> getRuntimeState(final GetRuntimeStateWorkerRequest request) {
        final var runtimeId = request.getRuntimeId();
        final var userId = securityIdentity.<Long>getAttribute("userId");

        return checkRuntimePermissionOperation.checkRuntimePermission(runtimeId,
                        userId,
                        RuntimePermissionEnum.HANDLE_RUNTIME)
                .flatMap(voidItem -> viewRuntimeCommands(runtimeId)
                        .flatMap(runtimeCommands -> collectPlayersOperation.collectPlayers(runtimeCommands)
                                .map(players -> new GetRuntimeStateWorkerResponse(runtimeCommands, players))
                        )
                );
    }

    Uni<List<RuntimeCommandModel>> viewRuntimeCommands(final Long runtimeId) {
        final var request = new ViewRuntimeCommandsRequest(runtimeId, false);
        return runtimeModule.getRuntimeService().viewRuntimeCommands(request)
                .map(ViewRuntimeCommandsResponse::getRuntimeCommands);
    }
}
