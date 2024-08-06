FROM registry:2.8.3
COPY src/main/docker/config.yaml /etc/docker/registry/config.yml
WORKDIR /etc/docker/registry
# Substitute values to config yaml
CMD (echo "cat <<EOF"; cat config.yml; echo -e "\nEOF" ) | sh > config.subst.yml && exec registry serve config.subst.yml