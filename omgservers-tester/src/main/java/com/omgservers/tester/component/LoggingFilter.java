package com.omgservers.tester.component;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class LoggingFilter implements Filter {

    final String apiName;

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {
        final var response = ctx.next(requestSpec, responseSpec);
        log.info("{}: {} {} -> {} {}, {}",
                apiName,
                requestSpec.getMethod(),
                requestSpec.getURI().substring(requestSpec.getURI().lastIndexOf("/")),
                requestSpec.getBody(),
                response.getStatusCode(),
                response.getBody().asString());
        return response;
    }
}
