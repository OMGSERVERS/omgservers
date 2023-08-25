package com.omgservers.dto.adminModule;

import com.omgservers.model.serviceAccount.ServiceAccountModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetServiceAccountAdminResponse {

    ServiceAccountModel serviceAccount;
}
