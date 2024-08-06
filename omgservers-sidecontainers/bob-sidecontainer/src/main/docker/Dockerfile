ARG OMGSERVERS_VERSION
FROM --platform=linux/amd64 openjdk:17-jdk-bullseye

# Install dependencies
# https://forum.defold.com/t/spine-4-1/72923/2
RUN apt-get update && \
    apt-get install -y --no-install-recommends libopenal-dev libgl1-mesa-dev libglw1-mesa-dev freeglut3-dev zip && \
    apt-get autoremove -y && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* && \
    mkdir /project

WORKDIR /project

RUN SHA1=$(curl -s http://d.defold.com/stable/info.json | sed 's/.*sha1": "\(.*\)".*/\1/') \
    && curl -L -o bob.jar "http://d.defold.com/archive/${SHA1}/bob/bob.jar"
