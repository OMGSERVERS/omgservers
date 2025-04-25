package com.omgservers.ctl.operation.wal;

import com.omgservers.ctl.dto.log.LogLineDto;

import java.nio.file.Path;

public interface WriteLogLineOperation {

    void execute(Path path, LogLineDto logLine);
}
