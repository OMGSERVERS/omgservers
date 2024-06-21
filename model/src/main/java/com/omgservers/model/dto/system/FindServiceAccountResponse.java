package com.omgservers.model.dto.system;

import com.omgservers.model.serviceAccount.ServiceAccountModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindServiceAccountResponse {

    ServiceAccountModel serviceAccount;
}
