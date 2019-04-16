# Stage 1 (to create a "build" image, ~140MB)
FROM anonymoussquad/maven3.5-jdk10-node AS builder
RUN java -version

COPY . /usr/src/myapp/
WORKDIR /usr/src/myapp/
RUN mvn package

# Stage 2 (to create a downsized "container executable", ~87MB)
FROM anonymoussquad/maven3.5-jdk10-node
WORKDIR /root/
COPY --from=builder /usr/src/myapp/ .

EXPOSE 8123
ENTRYPOINT ["sh", "./selector.sh"]