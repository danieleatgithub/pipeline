@Library('jenkins-shared-lib') _

def GROUPNAME = org.home.mythtv.Constants.VIDEO_GROUPNAME

pipeline {
    agent any

    environment {
        MYTH_BASEURL = "http://192.168.1.66:6544"
        LENOVOSRV_BASEURL = "http://192.168.1.49:18800"
        HOSTNAME = "i5-mythtv"
        FILE_TO_FIND = "example.mp4"
    }

    parameters {
        string(name: 'FILENAME', defaultValue: '', description: 'Nome del file da transcodificare')
        booleanParam(name: 'DRYRUN', defaultValue: true, description: 'Se TRUE non esegue')
    }

    stages {
        stage('Initialize') {
            steps {
                script {
                    // Environments init
                    env.CONFIG = loadConfigs("jobs/mythtv")
                    echo "Loaded config:" + env.CONFIG.toString()

                    // Runtime init
                    echo "Mounting mythtv ..."
                    def ret_mount = lenovosrvMountService(
                        baseUrl: LENOVOSRV_BASEURL,
                        action: 'mount',
                        mountTarget: 'mythtv'
                    )
                    echo "Done: " + ret_mount
                }
            }
        }
        stage('Get Storage Group Videos') {
            steps {
                script {
                    storageDirs = mythStorage(
                        baseUrl: MYTH_BASEURL,
                        action: 'list',
                        groupName: GROUPNAME,
                        hostName: HOSTNAME
                    ).StorageGroupList.StorageGroups.collect { it.DirName }
                    echo "Found storage dirs: ${storageDirs}"
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