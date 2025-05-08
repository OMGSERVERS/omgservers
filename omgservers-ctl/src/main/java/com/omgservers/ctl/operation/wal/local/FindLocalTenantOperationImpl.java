package com.omgservers.ctl.operation.wal.local;

import com.omgservers.ctl.dto.log.LogLineDto;
import com.omgservers.ctl.dto.log.LogLineQualifierEnum;
import com.omgservers.ctl.dto.log.body.LocalTenantLogLineBodyDto;
import com.omgservers.ctl.dto.wal.WalDto;
import com.omgservers.ctl.exception.CommandException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindLocalTenantOperationImpl implements FindLocalTenantOperation {

    @Override
    public LocalTenantLogLineBodyDto execute(final WalDto wal,
                                             final String tenant,
                                             final String project,
                                             final String stage) {
        return wal.getLogLines().stream()
                .filter(log -> log.getQualifier().equals(LogLineQualifierEnum.LOCAL_TENANT))
                .map(LogLineDto::getBody)
                .map(LocalTenantLogLineBodyDto.class::cast)
                .filter(logLine -> logLine.getTenant().equals(tenant))
                .filter(logLine -> logLine.getProject().equals(project))
                .filter(logLine -> logLine.getStage().equals(stage))
                .peek(logLine -> log.info("Local tenant \"{}\" found", tenant))
                .findFirst()
                .orElseThrow(() -> new CommandException("local tenant not found"));
    }
}
