package com.omgservers.ctl.operation.wal.admin;

import com.omgservers.ctl.dto.log.LogLineDto;
import com.omgservers.ctl.dto.log.LogLineQualifierEnum;
import com.omgservers.ctl.dto.log.body.AdminTokenLogLineBodyDto;
import com.omgservers.ctl.dto.wal.WalDto;
import com.omgservers.ctl.exception.CommandException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindAdminTokenOperationImpl implements FindAdminTokenOperation {

    @Override
    public AdminTokenLogLineBodyDto execute(final WalDto wal, final String installation) {
        return execute(wal, installation, null);
    }

    @Override
    public AdminTokenLogLineBodyDto execute(final WalDto wal,
                                            final String installation,
                                            final String user) {
        return wal.getLogLines().stream()
                .filter(log -> log.getQualifier().equals(LogLineQualifierEnum.ADMIN_TOKEN))
                .map(LogLineDto::getBody)
                .map(AdminTokenLogLineBodyDto.class::cast)
                .filter(logLine -> logLine.getInstallation().equals(installation))
                .filter(logLine -> {
                    if (user == null) {
                        return true;
                    } else {
                        return logLine.getUser().equals(user);
                    }
                })
                .peek(logLine -> log.info("Token for user \"{}\" found", logLine.getUser()))
                .findFirst()
                .orElseThrow(() -> new CommandException("token for admin user not found"));
    }
}
