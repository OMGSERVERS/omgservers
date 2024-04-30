FROM nickblah/luajit:2.1.0-beta3-luarocks-debian
RUN apt-get update && apt-get install -y build-essential
RUN luarocks install luasocket
RUN luarocks install lua-cjson
RUN luarocks install inspect
RUN luarocks install base64
