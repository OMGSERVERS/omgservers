package com.omgservers.application.module.userModule.impl.service.objectInternalService.response;

import com.omgservers.application.module.userModule.model.object.ObjectModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetObjectInternalResponse {

    ObjectModel object;
}
