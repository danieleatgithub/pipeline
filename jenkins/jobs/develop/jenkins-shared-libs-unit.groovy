pipeline {
    agent {
        docker {
            image 'gradle:8.11-jdk17'
            // Ottimizza le prestazioni riutilizzando la cache di Gradle tra le build
            args '-v $HOME/.gradle:/home/gradle/.gradle'
        }
    }

    stages {
        stage('Build & Test') {
            steps {
                // Usa lo script wrapper incluso nel tuo repo
                sh './gradlew clean test'
            }
            post {
                always {
                    // Pubblica i risultati dei test JUnit su Jenkins
                    junit 'build/test-results/test/*.xml'
                }
            }
        }

        stage('Publish') {
            when { branch 'main' }
            steps {
                sh './gradlew publish'
            }
        }
    }
}
