package com.omgservers.dto.internalModule;

import com.omgservers.model.serviceAccount.ServiceAccountModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetServiceAccountHelpResponse {

    ServiceAccountModel serviceAccount;
}
