package com.omgservers.model.dto.admin;

import com.omgservers.model.index.IndexModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateIndexAdminResponse {

    IndexModel index;
}
