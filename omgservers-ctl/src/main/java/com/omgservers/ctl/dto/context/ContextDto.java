package com.omgservers.ctl.dto.context;

import com.omgservers.ctl.dto.wal.WalDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContextDto {

    WalDto wal;
}
