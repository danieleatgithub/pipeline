pipeline {
    agent any

    environment {
        DOCKER_COMPOSE_PATH = 'jenkins-shared-lib'       // Sottocartella dove si trova docker-compose.yml
        BUILD_DIR = 'build-docker'                       // Cartella dedicata per Docker/Gradle build
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Prepare Workspace') {
            steps {
                dir("${DOCKER_COMPOSE_PATH}") {
                    // Creiamo la cartella build-docker se non esiste
                    sh """
                        mkdir -p ${BUILD_DIR}
                        chmod -R 777 ${BUILD_DIR}  # Permessi temporanei per il container gradle
                    """
                }
            }
        }

        stage('Unit Tests') {
            steps {
                dir("${DOCKER_COMPOSE_PATH}") {
                    // Build e run del container per i test
                    sh """
                        docker compose -f docker-compose.yml build jenkins-shared-lib-test
                        docker compose -f docker-compose.yml run --rm jenkins-shared-lib-test
                    """
                }
            }
        }

        stage('Debug Workspace') {
            steps {
                dir("${DOCKER_COMPOSE_PATH}") {
                    sh """
                        echo "Workspace content:"
                        ls -la
                        echo "Build directory content:"
                        ls -la ${BUILD_DIR}
                    """
                }
            }
        }
    }

    post {
        always {
            // Archivia i risultati dei test
            junit "${DOCKER_COMPOSE_PATH}/${BUILD_DIR}/test-results/test/TEST-*.xml"

            // Pubblica report HTML se presenti
            publishHTML([
                    allowMissing: true,
                    reportDir: "${DOCKER_COMPOSE_PATH}/${BUILD_DIR}/reports/tests/test",
                    reportFiles: 'index.html',
                    reportName: 'Unit Test Report'
            ])

            // Pulizia eventuali container o volumi Docker temporanei
            dir("${DOCKER_COMPOSE_PATH}") {
                sh 'docker compose down -v || true'
            }
        }
    }
}
