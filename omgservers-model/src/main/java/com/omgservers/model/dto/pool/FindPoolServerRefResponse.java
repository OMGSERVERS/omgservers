package com.omgservers.model.dto.pool;

import com.omgservers.model.poolServerRef.PoolServerRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPoolServerRefResponse {

    PoolServerRefModel poolServerRef;
}
