FROM gradle:8.5-jdk11

# Usa utente gradle (non root)
USER gradle

# Directory di lavoro
WORKDIR /app

# Copia solo i file necessari per risolvere le dipendenze
COPY --chown=gradle:gradle build.gradle settings.gradle ./

# Pre-scarica le dipendenze (cache layer)
RUN gradle dependencies --no-daemon || true

# Copia tutto il progetto
COPY --chown=gradle:gradle . .

# Comando di default: esegue il test specifico
CMD ["gradle", "test", "--tests", "org.home.myth.models.MythStorageServiceTest", "--no-daemon", "--info", "--stacktrace"]
