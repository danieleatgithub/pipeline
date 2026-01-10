@Library('jenkins-shared-lib') _

pipeline {
    agent any

    stages {
        stage('Hello') {
            steps {
                script {
                    def ret_hello = helloWorld( "*Hello*" ).
                            say().
                            setHello("Mondo").
                            say().
                            getHello()
                    echo "Done: " + ret_hello
                }
            }
        }
    }
}
