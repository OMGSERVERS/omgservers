package com.omgservers.ctl.dto.wal;

import com.omgservers.ctl.dto.log.LogLineDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.Path;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalDto {

    public static WalDto create(final Path path) {
        final var wal = new WalDto();
        wal.setPath(path);
        wal.setLogLines(List.of());
        return wal;
    }

    Path path;
    List<LogLineDto> logLines;
}
