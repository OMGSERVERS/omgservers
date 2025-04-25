package com.omgservers.ctl.operation.wal.admin;

import com.omgservers.ctl.dto.log.body.AdminTokenLogLineBodyDto;
import com.omgservers.ctl.dto.wal.WalDto;

public interface FindAdminTokenOperation {

    AdminTokenLogLineBodyDto execute(WalDto wal, String service);

    AdminTokenLogLineBodyDto execute(WalDto wal, String service, String user);
}
