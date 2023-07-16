package com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.response;

import com.omgservers.application.module.internalModule.model.serviceAccount.ServiceAccountModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetServiceAccountHelpResponse {

    ServiceAccountModel serviceAccount;
}
