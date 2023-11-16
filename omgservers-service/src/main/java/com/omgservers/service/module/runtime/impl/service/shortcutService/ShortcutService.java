package com.omgservers.service.module.runtime.impl.service.shortcutService;

import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.model.runtimePermission.RuntimePermissionModel;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ShortcutService {

    Uni<RuntimeModel> getRuntime(Long id);

    Uni<Boolean> syncRuntime(RuntimeModel runtime);

    Uni<Boolean> deleteRuntime(Long runtimeId);

    Uni<Boolean> syncRuntimeCommand(RuntimeCommandModel runtimeCommand);

    Uni<List<RuntimeCommandModel>> viewRuntimeCommands(final Long runtimeId);

    Uni<Boolean> deleteRuntimeCommand(final Long runtimeId, final Long id);

    Uni<Void> deleteRuntimeCommands(final Long runtimeId);

    Uni<List<RuntimePermissionModel>> viewRuntimePermissions(final Long runtimeId);

    Uni<Boolean> deleteRuntimePermission(final Long runtimeId, final Long id);

    Uni<Void> deleteRuntimePermissions(Long runtimeId);

    Uni<RuntimeGrantModel> findRuntimeGrant(Long runtimeId, Long clientId);

    Uni<List<RuntimeGrantModel>> viewRuntimeGrants(final Long runtimeId);

    Uni<Boolean> deleteRuntimeGrant(Long runtimeId, Long id);

    Uni<Void> deleteRuntimeGrants(final Long runtimeId);

}
