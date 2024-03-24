package com.omgservers;

import com.omgservers.model.user.UserRoleEnum;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.jwt.build.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

@Slf4j
@QuarkusTest
public class JwtTests extends Assertions {

    @Test
    void generateJwt() {
        String token = Jwt.issuer("https://omgservers.com")
                .upn("1234567890")
                .groups(Set.of(UserRoleEnum.Names.PLAYER))
                .sign();
        log.info("Jwt, {}", token);
    }
}
