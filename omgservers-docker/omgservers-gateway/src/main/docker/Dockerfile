FROM envoyproxy/envoy:v1.28-latest

RUN mkdir -p /var/log/envoy && \
    chown envoy:envoy /var/log/envoy
VOLUME /var/log/envoy
USER envoy