package com.omgservers.ctl.operation.wal.support;

import com.omgservers.ctl.dto.log.LogLineDto;
import com.omgservers.ctl.dto.log.LogLineQualifierEnum;
import com.omgservers.ctl.dto.log.body.SupportTokenLogLineBodyDto;
import com.omgservers.ctl.dto.wal.WalDto;
import com.omgservers.ctl.exception.CommandException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindSupportTokenOperationImpl implements FindSupportTokenOperation {

    @Override
    public SupportTokenLogLineBodyDto execute(final WalDto wal, final String service) {
        return execute(wal, service, null);
    }

    @Override
    public SupportTokenLogLineBodyDto execute(final WalDto wal,
                                              final String service,
                                              final String user) {
        return wal.getLogLines().stream()
                .filter(log -> log.getQualifier().equals(LogLineQualifierEnum.SUPPORT_TOKEN))
                .map(LogLineDto::getBody)
                .map(SupportTokenLogLineBodyDto.class::cast)
                .filter(logLine -> logLine.getInstallation().equals(service))
                .filter(logLine -> {
                    if (user == null) {
                        return true;
                    } else {
                        return logLine.getUser().equals(user);
                    }
                })
                .peek(logLine -> log.info("Token for support user \"{}\" found", logLine.getUser()))
                .findFirst()
                .orElseThrow(() -> new CommandException("token for support user not found"));
    }
}
