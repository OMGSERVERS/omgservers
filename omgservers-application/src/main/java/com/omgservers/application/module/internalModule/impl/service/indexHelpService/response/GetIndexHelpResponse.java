package com.omgservers.application.module.internalModule.impl.service.indexHelpService.response;

import com.omgservers.application.module.internalModule.model.index.IndexModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetIndexHelpResponse {

    IndexModel index;
}
