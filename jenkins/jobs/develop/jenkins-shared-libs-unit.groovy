pipeline {
    agent any

    options {
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Docker Check') {
            steps {
                sh 'whoami'
                sh 'groups'
                sh 'docker ps'
            }
        }
        stage('Unit Tests') {
            steps {
                dir('jenkins-shared-lib') {
                    sh '''
                      docker compose build jenkins-shared-lib-test
                      docker compose run --rm jenkins-shared-lib-test
                    '''
                }
            }
        }
    }

    post {
        always {
            junit 'build/test-results/test/**/*.xml'
        }
        cleanup {
            sh 'docker compose down -v || true'
        }
    }
}
