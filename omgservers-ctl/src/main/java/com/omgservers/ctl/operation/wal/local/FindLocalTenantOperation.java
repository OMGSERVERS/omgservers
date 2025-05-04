package com.omgservers.ctl.operation.wal.local;

import com.omgservers.ctl.dto.log.body.LocalTenantLogLineBodyDto;
import com.omgservers.ctl.dto.wal.WalDto;

public interface FindLocalTenantOperation {

    LocalTenantLogLineBodyDto execute(WalDto wal, String tenant, String project, String stage);
}
