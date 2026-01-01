@Library('jenkins-shared-lib@jenkins-interface-refactor') _

pipeline {
    agent any

    stages {
        stage('Hello') {
            steps {
                script {
                    def ret_hello = helloWorld( "*Hello*" ).
                            sayHello().
                            setHello("Mondo").
                            sayHello().
                            getHello()
                    echo "Done: " + ret_hello
                }
            }
        }
    }
}
