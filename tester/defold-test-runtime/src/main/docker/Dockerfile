ARG OMGSERVERS_VERSION
FROM --platform=linux/amd64 omgservers/bob:${OMGSERVERS_VERSION} as builder

COPY src/main/docker/defold-test-runtime .
RUN java -jar bob.jar --variant headless --platform x86_64-linux --archive \
    distclean resolve build bundle

FROM --platform=linux/amd64 ubuntu:latest

WORKDIR /game
COPY --from=builder /project/build/default/defold-test-runtime .
RUN ls -lah .

CMD ["./defoldtestruntime.x86_64"]