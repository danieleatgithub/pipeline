@Library('jenkins-shared-lib') _

pipeline {
    agent any

    stages {
        stage('Hello') {
            steps {
                script {
                    def ret_hello = helloWorld( "*World*" )
                    echo "Done: " + ret_hello
                }
            }
        }
    }
}
