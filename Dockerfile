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

RUN groupadd --system campfire \
    && useradd --system campfire --gid campfire \
    && chown -R campfire:campfire /app
USER campfire:campfire

COPY --from=builder /build/build/libs/docker/campfire.jar ./
CMD ["java", "-jar", "/app/campfire.jar"]
