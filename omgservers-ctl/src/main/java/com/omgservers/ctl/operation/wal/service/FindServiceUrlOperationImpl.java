package com.omgservers.ctl.operation.wal.service;

import com.omgservers.ctl.dto.log.LogLineDto;
import com.omgservers.ctl.dto.log.LogLineQualifierEnum;
import com.omgservers.ctl.dto.log.body.ServiceUrlLogLineBodyDto;
import com.omgservers.ctl.dto.wal.WalDto;
import com.omgservers.ctl.exception.CommandException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindServiceUrlOperationImpl implements FindServiceUrlOperation {

    @Override
    public ServiceUrlLogLineBodyDto execute(final WalDto wal) {
        return execute(wal, null);
    }

    @Override
    public ServiceUrlLogLineBodyDto execute(final WalDto wal,
                                            final String name) {
        return wal.getLogLines().stream()
                .filter(log -> log.getQualifier().equals(LogLineQualifierEnum.SERVICE_URL))
                .map(LogLineDto::getBody)
                .map(ServiceUrlLogLineBodyDto.class::cast)
                .filter(logLine -> {
                    if (name == null) {
                        return true;
                    } else {
                        return logLine.getName().equals(name);
                    }
                })
                .peek(logLine -> log.debug("Url of \"{}\" found, \"{}\"", logLine.getName(), logLine.getUri()))
                .findFirst()
                .orElseThrow(() -> new CommandException("url for service not found"));
    }
}
