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
                    """
                }
            }
        }

        stage('Unit Tests') {
            steps {
                dir("${DOCKER_COMPOSE_PATH}") {
                    withEnv([
                            'UID=115',
                            'GID=124'
                    ]) {
                        sh 'mkdir -p build-docker'
                        sh 'docker compose build jenkins-shared-lib-test'
                        sh 'docker compose run --rm jenkins-shared-lib-test'
                    }
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
            // Archivia i risultati dei test JUnit
            junit "${DOCKER_COMPOSE_PATH}/${BUILD_DIR}/test-results/test/TEST-*.xml"

            // Pubblica report HTML correttamente
            publishHTML([
                    reportDir: "${DOCKER_COMPOSE_PATH}/${BUILD_DIR}/reports/tests/test",
                    reportFiles: 'index.html',
                    reportName: 'Unit Test Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: true
            ])

            // Pulizia container/volumi Docker
            dir("${DOCKER_COMPOSE_PATH}") {
                sh 'docker compose down -v || true'
            }
        }
    }


}
