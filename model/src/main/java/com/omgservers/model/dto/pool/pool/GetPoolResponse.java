package com.omgservers.model.dto.pool.pool;

import com.omgservers.model.pool.PoolModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPoolResponse {

    PoolModel pool;
}
