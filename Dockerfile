FROM gradlew:jdk21
LABEL authors="Stanislav Efimtsev"
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle build

EXPOSE 8080
CMD java -jar ./build/libs/warehouse-0.0.1-SNAPSHOT.jar