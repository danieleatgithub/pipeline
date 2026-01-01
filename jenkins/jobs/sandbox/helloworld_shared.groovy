@Library('jenkins-shared-lib') _

pipeline {
    agent any

    stages {
        stage('Hello') {
            steps {
                script {
                    def ret_hello = helloWorld( "*Hello*" ).hello(" World ")
                    echo "Done: " + ret_hello
                }
            }
        }
    }
}
