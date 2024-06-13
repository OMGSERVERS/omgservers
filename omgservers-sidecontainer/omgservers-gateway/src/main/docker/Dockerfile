ARG OMGSERVERS_VERSION
FROM omgservers/omgservers-proxy:${OMGSERVERS_VERSION}
COPY src/main/docker/service12.yaml /etc/envoy/service12.yaml
COPY src/main/docker/service1ab.yaml /etc/envoy/service1ab.yaml
COPY src/main/docker/service2ab.yaml /etc/envoy/service2ab.yaml
COPY src/main/docker/service3ab.yaml /etc/envoy/service3ab.yaml
