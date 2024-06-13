FROM alpine/socat
CMD ["tcp-listen:2375,fork,reuseaddr", "unix-connect:/var/run/docker.sock"]