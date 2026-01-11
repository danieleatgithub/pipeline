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
                sh 'cd jenkins-shared-lib && ls -l'
                sh 'cd jenkins-shared-lib && ./gradlew clean test'
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
