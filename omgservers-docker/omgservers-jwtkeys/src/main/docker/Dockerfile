FROM ubuntu:latest

RUN useradd -u 185 -g 0 -c "JBoss user" -d /home/jboss -s /sbin/nologin jboss \
    && apt-get update && apt-get install -y openssl \
    && rm -rf /var/lib/apt/lists/* \
    && mkdir /jwtkeys \
    && chown jboss:root /jwtkeys

WORKDIR /jwtkeys

COPY --chown=jboss:root src/main/docker/generate_key_pair.sh .

VOLUME /jwtkeys
USER jboss

CMD ./generate_key_pair.sh service
