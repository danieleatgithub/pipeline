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
    }
}
