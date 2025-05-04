package com.omgservers.ctl.operation.wal.installation;

import com.omgservers.ctl.dto.log.body.InstallationDetailsLogLineBodyDto;
import com.omgservers.ctl.dto.wal.WalDto;

public interface FindInstallationDetailsOperation {

    InstallationDetailsLogLineBodyDto execute(WalDto wal);

    InstallationDetailsLogLineBodyDto execute(WalDto wal, String name);
}
