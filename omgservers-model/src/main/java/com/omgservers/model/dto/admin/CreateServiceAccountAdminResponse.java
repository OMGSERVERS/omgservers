package com.omgservers.model.dto.admin;

import com.omgservers.model.serviceAccount.ServiceAccountModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateServiceAccountAdminResponse {

    ServiceAccountModel serviceAccount;
}
