package com.omgservers.ctl.operation.command.ctl;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetDefaultWalPathOperation;
import com.omgservers.ctl.operation.wal.PurgeWalOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CtlPurgeWalOperationImpl implements CtlPurgeWalOperation {

    final GetDefaultWalPathOperation getDefaultWalPathOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final PurgeWalOperation purgeWalOperation;

    @Override
    public void execute() {
        final var path = getDefaultWalPathOperation.execute();
        purgeWalOperation.execute(path);

        appendResultMapOperation.execute(path, KeyEnum.RESULT, Boolean.TRUE.toString());
    }
}
