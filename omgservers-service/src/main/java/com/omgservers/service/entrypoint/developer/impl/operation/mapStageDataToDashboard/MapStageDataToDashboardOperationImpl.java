package com.omgservers.service.entrypoint.developer.impl.operation.mapStageDataToDashboard;

import com.omgservers.schema.entrypoint.developer.dto.StageDashboardDto;
import com.omgservers.schema.module.tenant.tenantStage.dto.TenantStageDataDto;
import com.omgservers.service.entrypoint.developer.impl.mappers.StageMapper;
import com.omgservers.service.entrypoint.developer.impl.mappers.VersionProjectionMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class MapStageDataToDashboardOperationImpl implements MapStageDataToDashboardOperation {

    final VersionProjectionMapper versionProjectionMapper;
    final StageMapper stageMapper;

    @Override
    public StageDashboardDto mapStageDataToDashboard(final TenantStageDataDto stageData) {
        final var stageDashboardDto = new StageDashboardDto();

        // Dashboard stage
        final var stageDto = stageMapper.modelToDto(stageData.getStage());
        stageDashboardDto.setStage(stageDto);

        // Stage version projections
        final var versionProjectionDtoList = stageData.getVersionProjections().stream()
                .map(versionProjectionMapper::modelToDto)
                .toList();
        stageDashboardDto.setVersionProjections(versionProjectionDtoList);

        return stageDashboardDto;
    }
}
