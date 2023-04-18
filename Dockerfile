#
# Build stage
#
FROM maven:3.9.1-amazoncorretto-17 as maven_build
WORKDIR /home/app/flashcards
# Cache
COPY pom.xml .
COPY src ./src
RUN --mount=type=cache,target=/root/.m2  mvn clean package -Dmaven.test.skip
RUN mkdir -p target/docker-packaging && cd target/docker-packaging && jar -xf ../*.jar

#
# Run stage
#
FROM amazoncorretto:17-alpine-jdk
WORKDIR /home/app/flashcards
ARG DOCKER_PACKAGING_DIR=/home/app/flashcards/target/docker-packaging
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/BOOT-INF/lib /home/app/flashcards/lib
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/BOOT-INF/classes /home/app/flashcards/classes
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/META-INF /home/app/flashcards/META-INF
CMD java -cp .:classes:lib/* \
         -Djava.security.egd=file:/dev/./urandom \
         cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.FlashcardsApplication
