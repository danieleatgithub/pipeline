@NonCPS
def getConstants() {
    return [
            VIDEO_GROUPNAME: org.home.myth.Constants.VIDEO_GROUPNAME
    ]
}

def call() {
    return getConstants()
}
