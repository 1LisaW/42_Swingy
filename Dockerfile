FROM maven:3.9.11-eclipse-temurin-21

WORKDIR /workspace

# The project will be mounted here
VOLUME ["/workspace/my-swing-app"]

# Default command builds the mounted project
CMD ["mvn", "-f", "/workspace/my-swing-app/pom.xml", "clean", "package"]