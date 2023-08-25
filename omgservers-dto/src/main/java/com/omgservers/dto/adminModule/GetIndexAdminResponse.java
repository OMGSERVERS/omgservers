package com.omgservers.dto.adminModule;

import com.omgservers.model.index.IndexModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetIndexAdminResponse {

    IndexModel index;
}
