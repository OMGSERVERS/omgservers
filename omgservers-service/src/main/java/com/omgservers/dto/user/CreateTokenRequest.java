package com.omgservers.dto.user;

import com.omgservers.dto.ShardedRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenRequest implements ShardedRequest {

    @NotNull
    Long userId;

    @NotBlank
    @Size(max = 64)
    @ToString.Exclude
    String password;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
