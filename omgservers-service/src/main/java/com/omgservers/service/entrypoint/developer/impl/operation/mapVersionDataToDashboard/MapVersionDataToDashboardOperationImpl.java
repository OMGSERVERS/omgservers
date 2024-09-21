package com.omgservers.service.entrypoint.developer.impl.operation.mapVersionDataToDashboard;

import com.omgservers.schema.entrypoint.developer.dto.VersionDashboardDto;
import com.omgservers.schema.module.tenant.tenantVersion.dto.TenantVersionDataDto;
import com.omgservers.service.entrypoint.developer.impl.mappers.VersionImageRefMapper;
import com.omgservers.service.entrypoint.developer.impl.mappers.VersionLobbyRefMapper;
import com.omgservers.service.entrypoint.developer.impl.mappers.VersionMapper;
import com.omgservers.service.entrypoint.developer.impl.mappers.VersionMatchmakerRefMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class MapVersionDataToDashboardOperationImpl implements MapVersionDataToDashboardOperation {

    final VersionMatchmakerRefMapper versionMatchmakerRefMapper;
    final VersionLobbyRefMapper versionLobbyRefMapper;
    final VersionImageRefMapper versionImageRefMapper;
    final VersionMapper versionMapper;

    @Override
    public VersionDashboardDto mapVersionDataToDashboard(final TenantVersionDataDto versionData) {
        final var versionDashboardDto = new VersionDashboardDto();

        // Dashboard version
        final var versionDto = versionMapper.modelToDto(versionData.getTenantVersion());

        // Version images
        final var versionImageDtoList = versionData.getTenantImageRefs().stream()
                .map(versionImageRefMapper::modelToDto)
                .toList();
        versionDto.setImageRefs(versionImageDtoList);

        // Version lobbies
        final var versionLobbyDtoList = versionData.getTenantLobbyRefs().stream()
                .map(versionLobbyRefMapper::modelToDto)
                .toList();
        versionDto.setLobbyRefs(versionLobbyDtoList);

        // Version matchmakers
        final var versionMatchmakerDtoList = versionData.getTenantMatchmakerRefs().stream()
                .map(versionMatchmakerRefMapper::modelToDto)
                .toList();
        versionDto.setMatchmakerRefs(versionMatchmakerDtoList);

        versionDashboardDto.setVersion(versionDto);

        return versionDashboardDto;
    }
}
