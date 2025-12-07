pipeline {
    agent any

    stages {
        stage('Hello') {
            steps {
                script {
                    println "Hello World!"
                }
            }
        }
    }
}
