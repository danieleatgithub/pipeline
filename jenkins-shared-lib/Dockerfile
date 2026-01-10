FROM gradle:8.5-jdk11

ENV TZ=Europe/Rome
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


# Directory di lavoro
WORKDIR /app

# Copia solo i file necessari per risolvere le dipendenze
COPY build.gradle settings.gradle ./

# Pre-scarica le dipendenze (cache layer)
RUN gradle dependencies --no-daemon || true

# Copia tutto il progetto
COPY . .

CMD ["gradle", "test", "--no-daemon", "--info", "--stacktrace"]