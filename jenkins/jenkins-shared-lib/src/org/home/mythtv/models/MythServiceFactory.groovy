package org.home.mythtv.models

class MythServiceFactory implements Serializable {

    def steps
    String baseUrl

    MythServiceFactory(steps, String baseUrl) {
        this.steps = steps
        this.baseUrl = baseUrl
    }

    MythStorageService storageService() {
        return new MythStorageService(steps, baseUrl)
    }

    // futuro:
    // MythRecordingService
    // MythVideoService
}
