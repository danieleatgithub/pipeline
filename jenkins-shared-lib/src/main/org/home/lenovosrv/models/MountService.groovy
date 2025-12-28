package org.home.lenovosrv.models

import org.home.models.HttpClient

class MountService implements Serializable {

    private final HttpClient client

    // riceve HttpClient già inizializzato dalla factory
    MountService(HttpClient client) {
        this.client = client
    }

    /**
     * GET /mount
     */
    Map status() {
        return client.get("/mount/", ['Accept': 'application/json'])
    }

    /**
     * POST /mount con mount: true
     */
    Map mount(String mountTarget) {
        return client.post("/mount/", [
            mount_target: mountTarget,
            mount       : true
        ], ['Accept': 'application/json'])
    }

    /**
     * POST /mount con mount: false
     */
    Map umount(String mountTarget) {
        return client.post("/mount/", [
            mount_target: mountTarget,
            mount       : false
        ], ['Accept': 'application/json'])
    }

    /**
     * Filtra i mount point per tipo filesystem
     */
    List<Map> getMountsByFsType(String fstype) {
        def res = status()
        return (res?.mounts?.findAll { it.fstype == fstype } ?: []) as List<Map>
    }

}
