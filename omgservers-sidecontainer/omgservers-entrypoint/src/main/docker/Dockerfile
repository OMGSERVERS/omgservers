ARG OMGSERVERS_VERSION
FROM omgservers/omgservers-proxy:${OMGSERVERS_VERSION}
COPY src/main/docker/standalone1.yaml /etc/envoy/standalone1.yaml
COPY src/main/docker/standalone2.yaml /etc/envoy/standalone2.yaml
COPY src/main/docker/standalone3.yaml /etc/envoy/standalone3.yaml