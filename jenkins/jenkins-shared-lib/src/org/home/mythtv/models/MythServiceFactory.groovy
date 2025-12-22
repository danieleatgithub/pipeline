package org.home.mythtv.models

class MythServiceFactory implements Serializable {

    def steps
    String baseUrl
    private final HttpClient client

    MythServiceFactory(steps, String baseUrl) {
        this.steps = steps
        this.baseUrl = baseUrl
        this.client = new HttpClient(steps, baseUrl)
    }

    MythStorageService storageService() {
        return new MythStorageService(client)
    }

}
