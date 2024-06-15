FROM alpine:latest
RUN apk update && apk add --no-cache bash certbot
COPY src/main/docker/request_edge_certificate.sh /root
RUN chmod +x /root/request_edge_certificate.sh
WORKDIR /root
CMD tail -f /dev/null
