package com.omgservers.dto.user;

import com.omgservers.model.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCredentialsShardedResponse {

    UserModel user;
}
