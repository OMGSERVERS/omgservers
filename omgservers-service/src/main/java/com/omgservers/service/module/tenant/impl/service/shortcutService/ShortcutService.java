package com.omgservers.service.module.tenant.impl.service.shortcutService;

import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.projectPermission.ProjectPermissionModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.stagePermission.StagePermissionModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.tenantPermission.TenantPermissionModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ShortcutService {
    Uni<TenantModel> getTenant(Long id);

    Uni<List<TenantPermissionModel>> viewTenantPermissions(Long tenantId);

    Uni<Boolean> deleteTenantPermission(Long tenantId, Long id);

    Uni<Void> deleteTenantPermissions(Long tenantId);

    Uni<ProjectModel> getProject(Long tenantId, Long id);

    Uni<List<ProjectModel>> viewProjects(Long tenantId);

    Uni<Boolean> deleteProject(Long tenantId, Long id);

    Uni<Void> deleteProjects(Long tenantId);

    Uni<List<ProjectPermissionModel>> viewProjectPermissions(Long tenantId, Long projectId);

    Uni<Boolean> deleteProjectPermission(Long tenantId, Long id);

    Uni<Void> deleteProjectPermissions(Long tenantId, Long projectId);

    Uni<Void> validateStageSecret(Long tenantId, Long stageId, String secret);

    Uni<StageModel> getStage(Long tenantId, Long id);

    Uni<Long> findStageVersionId(Long tenantId, Long stageId);

    Uni<List<StageModel>> viewStages(Long tenantId, Long projectId);

    Uni<Boolean> deleteStage(Long tenantId, Long id);

    Uni<Void> deleteStages(Long tenantId, Long projectId);

    Uni<List<StagePermissionModel>> viewStagePermissions(Long tenantId, Long stageId);

    Uni<Boolean> deleteStagePermission(Long tenantId, Long id);

    Uni<Void> deleteStagePermissions(Long tenantId, Long stageId);

    Uni<VersionModel> getVersion(Long tenantId, Long id);

    Uni<List<VersionModel>> viewVersions(Long tenantId, Long stageId);

    Uni<Boolean> deleteVersion(Long tenantId, Long id);

    Uni<Void> deleteVersions(Long tenantId, Long stageId);

    Uni<VersionMatchmakerModel> selectVersionMatchmaker(Long tenantId, Long versionId);

    Uni<VersionRuntimeModel> selectVersionRuntime(Long tenantId, Long versionId);

    Uni<VersionMatchmakerModel> getVersionMatchmaker(Long tenantId, Long id);

    Uni<List<VersionMatchmakerModel>> viewVersionMatchmakers(Long tenantId, Long versionId);

    Uni<Boolean> deleteVersionMatchmaker(Long tenantId, Long id);

    Uni<Boolean> deleteVersionMatchmakers(Long tenantId, Long versionId);

    Uni<VersionConfigModel> getVersionConfig(Long tenantId, Long versionId);

    Uni<VersionRuntimeModel> getVersionRuntime(Long tenantId, Long id);

    Uni<List<VersionRuntimeModel>> viewVersionRuntimes(Long tenantId, Long versionId);

    Uni<Boolean> deleteVersionRuntime(Long tenantId, Long id);

    Uni<Boolean> deleteVersionRuntimes(Long tenantId, Long versionId);

}
