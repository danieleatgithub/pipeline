package org.home.mythtv.models

import org.home.models.HttpClient

class MythStorageService implements Serializable {

    private final HttpClient client

    // Ora riceve HttpClient già inizializzato dalla factory
    MythStorageService(HttpClient client) {
        this.client = client
    }

    Map getStorageGroupDirs() {
        return client.get("/Myth/GetStorageGroupDirs", ['Accept':'application/json'])
    }

    Map getStorageGroupList(String groupName = null, String hostName = null) {
        def query = []
        if (groupName) query << "GroupName=${URLEncoder.encode(groupName, 'UTF-8')}"
        if (hostName)  query << "HostName=${URLEncoder.encode(hostName, 'UTF-8')}"
        def path = "/Myth/GetStorageGroupList" + (query ? "?" + query.join("&") : "")
        return client.get(path, ['Accept':'application/json'])
    }

    Map addStorageGroupDir(String dirName, String groupName, String hostName) {
        return client.post("/Myth/AddStorageGroupDir", [
            DirName  : dirName,
            GroupName: groupName,
            HostName : hostName
        ], ['Accept':'application/json'])
    }

    Map removeStorageGroupDir(String dirName, String groupName, String hostName) {
        return client.post("/Myth/RemoveStorageGroupDir", [
            DirName  : dirName,
            GroupName: groupName,
            HostName : hostName
        ], ['Accept':'application/json'])
    }

    List<Map> getDirsByGroup(String groupName) {
        def res = getStorageGroupDirs()
        return res?.StorageGroupDirList?.StorageGroupDirs?.findAll { it.GroupName == groupName } ?: []
    }
}
