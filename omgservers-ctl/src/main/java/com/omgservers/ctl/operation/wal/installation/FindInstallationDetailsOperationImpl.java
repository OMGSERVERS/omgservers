package com.omgservers.ctl.operation.wal.installation;

import com.omgservers.ctl.dto.log.LogLineDto;
import com.omgservers.ctl.dto.log.LogLineQualifierEnum;
import com.omgservers.ctl.dto.log.body.InstallationDetailsLogLineBodyDto;
import com.omgservers.ctl.dto.wal.WalDto;
import com.omgservers.ctl.exception.CommandException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindInstallationDetailsOperationImpl implements FindInstallationDetailsOperation {

    @Override
    public InstallationDetailsLogLineBodyDto execute(final WalDto wal) {
        return execute(wal, null);
    }

    @Override
    public InstallationDetailsLogLineBodyDto execute(final WalDto wal,
                                                     final String name) {
        return wal.getLogLines().stream()
                .filter(log -> log.getQualifier().equals(LogLineQualifierEnum.INSTALLATION_DETAILS))
                .map(LogLineDto::getBody)
                .map(InstallationDetailsLogLineBodyDto.class::cast)
                .filter(logLine -> {
                    if (name == null) {
                        return true;
                    } else {
                        return logLine.getName().equals(name);
                    }
                })
                .peek(logLine -> log.info("Installation \"{}\"'s details found, \"{}\"", logLine.getName(), logLine.getApi()))
                .findFirst()
                .orElseThrow(() -> new CommandException("installation details not found"));
    }
}
