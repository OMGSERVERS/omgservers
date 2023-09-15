package com.omgservers.dto.admin;

import com.omgservers.model.index.IndexModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncIndexAdminRequest {

    @NotNull
    IndexModel index;
}
