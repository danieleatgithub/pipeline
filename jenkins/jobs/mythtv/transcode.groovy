@Library('jenkins-shared-lib') _

pipeline {
    agent any

    parameters {
        string(name: 'TAG', defaultValue: '_4Transcode', description: 'Genere MythTV da processare')
        booleanParam(name: 'DRYRUN', defaultValue: true, description: 'Se TRUE non esegue')
    }

    stages {
        stage('Initialize') {
            steps {
                script {
                    env.CONFIG = loadConfigs("jobs/mythtv")
                    echo "Loaded config:" + env.CONFIG.toString()
                }
            }
        }
        stage('Get video') {
            steps {
                script {
                    println "Cerco il prossimo video da processare"
                    echo "Loaded config:"
                    echo env.CONFIG.toString()
                }
            }
        }
        stage('Start Transcoding') {
            steps {
                script {
                    println "trascodifico"
                }
            }
        }
        stage('Update metainfo') {
            steps {
                script {
                    println "Aggiorno db e metadati per job che fara la copia"
                }
            }
        }
    }
}