package org.home.myth.models

import org.home.models.HttpClient
import spock.lang.Specification
import spock.lang.Unroll

class MythStorageServiceTest extends Specification {

    HttpClient mockClient
    MythStorageService service

    def setup() {
        mockClient = Mock(HttpClient)
        service = new MythStorageService(mockClient)
    }

    def "should get storage group directories"() {
        given: "a mock response"
        def expectedResponse = [
                StorageGroupDirList: [
                        StorageGroupDirs: [
                                [GroupName: 'Default', DirName: '/var/lib/mythtv', HostName: 'backend1'],
                                [GroupName: 'Videos', DirName: '/mnt/videos', HostName: 'backend1']
                        ]
                ]
        ]

        when: "calling getStorageGroupDirs"
        def result = service.getStorageGroupDirs()

        then: "client.get is called with correct parameters"
        1 * mockClient.get('/Myth/GetStorageGroupDirs', ['Accept': 'application/json']) >> expectedResponse

        and: "result matches expected response"
        result == expectedResponse
    }

    @Unroll
    def "should get storage group list with groupName=#groupName and hostName=#hostName"() {
        given: "a mock response"
        def expectedResponse = [StorageGroupDirList: [:]]

        when: "calling getStorageGroupList"
        def result = service.getStorageGroupList(groupName, hostName)

        then: "client.get is called with correct path and headers"
        1 * mockClient.get(expectedPath, ['Accept': 'application/json']) >> expectedResponse
        result == expectedResponse

        where:
        groupName | hostName   | expectedPath
        null      | null       | '/Myth/GetStorageGroupDirs'
        'Default' | null       | '/Myth/GetStorageGroupDirs?GroupName=Default'
        null      | 'backend1' | '/Myth/GetStorageGroupDirs?HostName=backend1'
        'Default' | 'backend1' | '/Myth/GetStorageGroupDirs?GroupName=Default&HostName=backend1'
    }

    def "should encode special characters in getStorageGroupList parameters"() {
        given: "parameters with special characters"
        def groupName = 'My Videos'
        def hostName = 'backend@home'
        def expectedResponse = [:]

        when: "calling getStorageGroupList"
        service.getStorageGroupList(groupName, hostName)

        then: "parameters are URL encoded"
        1 * mockClient.get(
                '/Myth/GetStorageGroupDirs?GroupName=My+Videos&HostName=backend%40home',
                ['Accept': 'application/json']
        ) >> expectedResponse
    }

    def "should add storage group directory"() {
        given: "directory parameters"
        def dirName = '/mnt/new_storage'
        def groupName = 'Videos'
        def hostName = 'backend1'
        def expectedResponse = [Success: true]

        when: "calling addStorageGroupDir"
        def result = service.addStorageGroupDir(dirName, groupName, hostName)

        then: "client.post is called with correct parameters"
        1 * mockClient.post(
                '/Myth/GetStorageGroupDirs',
                [DirName: dirName, GroupName: groupName, HostName: hostName],
                ['Accept': 'application/json']
        ) >> expectedResponse

        and: "result matches expected response"
        result == expectedResponse
    }

    def "should remove storage group directory"() {
        given: "directory parameters"
        def dirName = '/mnt/old_storage'
        def groupName = 'Videos'
        def hostName = 'backend1'
        def expectedResponse = [Success: true]

        when: "calling removeStorageGroupDir"
        def result = service.removeStorageGroupDir(dirName, groupName, hostName)

        then: "client.post is called with correct parameters"
        1 * mockClient.post(
                '/Myth/GetStorageGroupDirs',
                [DirName: dirName, GroupName: groupName, HostName: hostName],
                ['Accept': 'application/json']
        ) >> expectedResponse

        and: "result matches expected response"
        result == expectedResponse
    }

    def "should get directories filtered by group name"() {
        given: "a response with multiple groups"
        def mockResponse = [
                StorageGroupDirList: [
                        StorageGroupDirs: [
                                [GroupName: 'Default', DirName: '/var/lib/mythtv', HostName: 'backend1'],
                                [GroupName: 'Videos', DirName: '/mnt/videos', HostName: 'backend1'],
                                [GroupName: 'Videos', DirName: '/mnt/videos2', HostName: 'backend2'],
                                [GroupName: 'Music', DirName: '/mnt/music', HostName: 'backend1']
                        ]
                ]
        ]
        mockClient.get('/Myth/GetStorageGroupDirs', ['Accept': 'application/json']) >> mockResponse

        when: "calling getDirsByGroup with 'Videos'"
        def result = service.getDirsByGroup('Videos')

        then: "only Videos directories are returned"
        result.size() == 2
        result.every { it.GroupName == 'Videos' }
        result*.DirName.containsAll(['/mnt/videos', '/mnt/videos2'])
    }

    def "should return empty list when group not found"() {
        given: "a response without the requested group"
        def mockResponse = [
                StorageGroupDirList: [
                        StorageGroupDirs: [
                                [GroupName: 'Default', DirName: '/var/lib/mythtv', HostName: 'backend1']
                        ]
                ]
        ]
        mockClient.get('/Myth/GetStorageGroupDirs', ['Accept': 'application/json']) >> mockResponse

        when: "calling getDirsByGroup with non-existent group"
        def result = service.getDirsByGroup('NonExistent')

        then: "empty list is returned"
        result == []
    }

    def "should handle null response in getDirsByGroup"() {
        given: "a null response"
        mockClient.get('/Myth/GetStorageGroupDirs', ['Accept': 'application/json']) >> null

        when: "calling getDirsByGroup"
        def result = service.getDirsByGroup('Videos')

        then: "empty list is returned"
        result == []
    }

    def "should handle missing StorageGroupDirs in response"() {
        given: "a response with missing StorageGroupDirs"
        def mockResponse = [StorageGroupDirList: [:]]
        mockClient.get('/Myth/GetStorageGroupDirs', ['Accept': 'application/json']) >> mockResponse

        when: "calling getDirsByGroup"
        def result = service.getDirsByGroup('Videos')

        then: "empty list is returned"
        result == []
    }

    def "should be serializable"() {
        expect: "class implements Serializable"
        service instanceof Serializable
    }
}




