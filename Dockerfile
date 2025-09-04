FROM maven:3.9.9-eclipse-temurin-21

# Install Chromium + Chromedriver and common deps (multi-arch)
RUN apt-get update && apt-get install -y --no-install-recommends \
    chromium chromium-driver xvfb ca-certificates \
    fonts-liberation libasound2t64 libatk-bridge2.0-0 libnss3 libxss1 \
    libx11-xcb1 libxcomposite1 libxrandr2 libgbm1 libgtk-3-0 libu2f-udev libvulkan1 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY pom.xml .
RUN mvn -q -DskipTests dependency:resolve dependency:resolve-plugins
COPY src ./src

# Point Selenium to Chromium binary if needed
ENV CHROME_BIN=/usr/bin/chromium
ENV CHROMEDRIVER=/usr/bin/chromedriver
ENV CHROME_HEADLESS=true
ENV _JAVA_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
ENV MAVEN_OPTS="-Xmx1024m"

CMD ["mvn", "-q", "-e", "test"]