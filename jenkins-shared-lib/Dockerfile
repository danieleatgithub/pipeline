FROM gradle:8.5-jdk11

USER root

# Librerie native richieste da Gradle
RUN apt-get update && apt-get install -y \
    libc6 \
    libc6-dev \
    libstdc++6 \
    zlib1g \
    zlib1g-dev \
    && rm -rf /var/lib/apt/lists/*

ENV TZ=Europe/Rome
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /app

# Copia solo i file necessari per risolvere le dipendenze
COPY build.gradle settings.gradle ./

# Pre-scarica le dipendenze (cache layer)
RUN gradle dependencies --no-daemon || true

# Copia tutto il progetto
COPY . .

CMD ["gradle", "test", "--no-daemon", "--info", "--stacktrace"]
