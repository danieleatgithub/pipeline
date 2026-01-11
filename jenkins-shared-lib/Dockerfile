# Utilizzo dell'immagine ufficiale Gradle con JDK 11
FROM gradle:8.5-jdk11

# Impostazione dell'utente root temporaneamente per le configurazioni di sistema
USER root

# Configurazione del fuso orario (Europe/Rome)
ENV TZ=Europe/Rome
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Definizione della directory di lavoro
WORKDIR /app

# Creazione della cartella build e gestione permessi per l'utente gradle
# Questo evita problemi di scrittura quando si montano volumi esterni
RUN mkdir -p /app/build && chown -R gradle:gradle /app

# Passaggio all'utente non-root predefinito dell'immagine per sicurezza
USER gradle

# Copia dei file di configurazione delle dipendenze con i permessi corretti
# Iniziamo solo con questi per massimizzare l'uso della cache di Docker
COPY --chown=gradle:gradle build.gradle settings.gradle ./

# Scaricamento delle dipendenze (cache layer)
# Il comando 'dependencies' forza il download senza eseguire i test
RUN gradle dependencies --no-daemon --info || true

# Copia di tutto il resto del progetto
COPY --chown=gradle:gradle . .

# Comando di default per l'esecuzione dei test
# --no-daemon è essenziale in ambienti Docker/CI
# --build-cache sfrutta le ottimizzazioni di Gradle 8.x
CMD ["gradle", "test", "--no-daemon", "--info", "--stacktrace", "--build-cache"]
