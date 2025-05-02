package com.omgservers.ctl.operation.wal.localtesting;

import com.omgservers.ctl.dto.log.body.TestTenantLogLineBodyDto;
import com.omgservers.ctl.dto.wal.WalDto;

public interface FindTestTenantOperation {

    TestTenantLogLineBodyDto execute(WalDto wal, String tenant, String project, String stage);
}
