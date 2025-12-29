package org.home.lenovosrv.models

import org.home.models.HttpClient

class LenovosrvServiceFactory implements Serializable {

    private final def steps
    private final String baseUrl
    private final HttpClient client

    LenovosrvServiceFactory(def steps, String baseUrl) {
        this.steps = steps
        this.baseUrl = baseUrl
        this.client = new HttpClient(steps, baseUrl)
    }

    MountService mountService() {
        return new MountService(client)
    }
}
