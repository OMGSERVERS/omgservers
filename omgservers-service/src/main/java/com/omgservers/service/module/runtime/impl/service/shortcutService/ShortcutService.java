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

    Uni<List<RuntimeCommandModel>> viewRuntimeCommands(Long runtimeId);

    Uni<Boolean> deleteRuntimeCommand(Long runtimeId, Long id);

    Uni<Void> deleteRuntimeCommands(Long runtimeId);

    Uni<List<RuntimePermissionModel>> viewRuntimePermissions(Long runtimeId);

    Uni<Boolean> syncRuntimePermission(RuntimePermissionModel runtimePermission);

    Uni<Boolean> deleteRuntimePermission(Long runtimeId, Long id);

    Uni<Void> deleteRuntimePermissions(Long runtimeId);

    Uni<RuntimeGrantModel> findRuntimeGrant(Long runtimeId, Long clientId);

    Uni<List<RuntimeGrantModel>> viewRuntimeGrants(Long runtimeId);

    Uni<Boolean> syncRuntimeGrant(RuntimeGrantModel runtimeGrant);

    Uni<Boolean> deleteRuntimeGrant(Long runtimeId, Long id);

    Uni<Void> deleteRuntimeGrants(Long runtimeId);

}
