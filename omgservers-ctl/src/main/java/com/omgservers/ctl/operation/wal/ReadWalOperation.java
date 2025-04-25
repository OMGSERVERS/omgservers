package com.omgservers.ctl.operation.wal;

import com.omgservers.ctl.dto.wal.WalDto;

import java.nio.file.Path;

public interface ReadWalOperation {

    WalDto execute(Path path);
}
