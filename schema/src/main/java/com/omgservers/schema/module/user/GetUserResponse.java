package com.omgservers.schema.module.user;

import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {

    UserModel user;
}
