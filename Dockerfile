ARG JAVA_VERSION=17
ARG JVM_FLAVOR=hotspot

FROM openjdk:${JAVA_VERSION}-jdk-slim AS builder
WORKDIR /build

COPY ./ ./
RUN ./gradlew clean buildForDocker --no-daemon

ARG JAVA_VERSION
ARG JVM_FLAVOR

FROM openjdk:${JAVA_VERSION}-slim
WORKDIR /app

RUN groupadd --system nature \
    && useradd --system nature --gid nature \
    && chown -R nature:nature /app
USER nature:nature

COPY --from=builder /build/build/libs/docker/nature.jar ./
CMD ["java", "-jar", "/app/nature.jar"]
