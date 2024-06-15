FROM alpine:latest
RUN apk update && apk add --no-cache bash certbot
COPY src/main/docker/*.sh /root
RUN chmod +x /root/*.sh
WORKDIR /root
CMD tail -f /dev/null
