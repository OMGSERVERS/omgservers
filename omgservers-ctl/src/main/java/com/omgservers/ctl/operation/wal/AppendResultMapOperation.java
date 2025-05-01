package com.omgservers.ctl.operation.wal;

import com.omgservers.ctl.dto.key.KeyEnum;

import java.nio.file.Path;
import java.util.Map;

public interface AppendResultMapOperation {

    void execute(Path path, KeyEnum key, String value);

    void execute(Path path, Map<KeyEnum, String> map);
}
