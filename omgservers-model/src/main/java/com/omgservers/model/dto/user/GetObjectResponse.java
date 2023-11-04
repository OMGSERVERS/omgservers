package com.omgservers.model.dto.user;

import com.omgservers.model.object.ObjectModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetObjectResponse {

    ObjectModel object;
}
