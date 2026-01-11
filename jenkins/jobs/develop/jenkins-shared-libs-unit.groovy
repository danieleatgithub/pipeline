pipeline {
    agent {
        docker {
            image 'gradle:8.11-jdk17'
            args '-u root --entrypoint=""'
        }
    }

    stages {
        stage('Build & Test') {
            steps {
                sh "git config --global --add safe.directory ${WORKSPACE}"
                sh 'cd jenkins-shared-lib && ./gradlew clean test'
                sh 'cd jenkins-shared-lib && ls -l'
            }
            post {
                always {
                    // Pubblica i risultati dei test JUnit su Jenkins
                    junit 'jenkins-shared-lib/build/test-results/test/*.xml'
                }
            }
        }

        stage('Publish') {
            when {
                anyOf {
                    branch 'master'
                    branch 'unstable'
                }
            }
            steps {
                sh 'cd jenkins-shared-lib && ./gradlew publish'
            }
        }
        post {
            success {
                archiveArtifacts artifacts: 'jenkins-shared-lib/build/libs/*.jar', fingerprint: true
                echo "JAR archiviato con successo su Jenkins!"
            }
            always {
                junit '**/build/test-results/**/*.xml'
            }
        }
    }
}
