package com.omgservers.ctl.operation.wal.developer;

import com.omgservers.ctl.dto.log.body.DeveloperTokenLogLineBodyDto;
import com.omgservers.ctl.dto.wal.WalDto;

public interface FindDeveloperTokenOperation {

    DeveloperTokenLogLineBodyDto execute(WalDto wal, String service);

    DeveloperTokenLogLineBodyDto execute(WalDto wal, String service, String user);
}
