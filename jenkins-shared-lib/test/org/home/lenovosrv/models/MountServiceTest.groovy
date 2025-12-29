package org.home.lenovosrv.models

import org.home.models.HttpClient
import spock.lang.Specification

class MountServiceTest extends Specification {

    def client = Mock(HttpClient)
    def service = new MountService(client)

    def "status() deve chiamare GET /mount/ e restituire la risposta"() {
        given:
        def response = [mounts: []] as Map<String, Object>

        when:
        def result = service.status()

        then:
        1 * client.get("/mount/", ['Accept': 'application/json']) >> response
        result == response
    }

    def "mount() deve chiamare POST /mount/ con mount=true"() {
        given:
        def response = [ok: true] as Map<String, Object>

        when:
        def result = service.mount("/dev/sda1")

        then:
        1 * client.post("/mount/", [
                mount_target: "/dev/sda1",
                mount       : true
        ], ['Accept': 'application/json']) >> response

        result == response
    }

    def "umount() deve chiamare POST /mount/ con mount=false"() {
        given:
        def response = [ok: true] as Map<String, Object>

        when:
        def result = service.umount("/dev/sda1")

        then:
        1 * client.post("/mount/", [
                mount_target: "/dev/sda1",
                mount       : false
        ], ['Accept': 'application/json']) >> response

        result == response
    }

    def "getMountsByFsType() deve filtrare i mount point per fstype"() {
        given:
        def response = [
                mounts: [
                        [fstype: "ext4", path: "/"],
                        [fstype: "vfat", path: "/boot"],
                        [fstype: "ext4", path: "/home"]
                ]
        ]

        client.get("/mount/", ['Accept': 'application/json']) >> response

        when:
        def result = service.getMountsByFsType("ext4")

        then:
        result.size() == 2
        result*.path == ["/", "/home"]
    }

    def "getMountsByFsType() deve restituire lista vuota se mounts è null"() {
        given:
        client.get("/mount/", ['Accept': 'application/json']) >> [:]

        when:
        def result = service.getMountsByFsType("ext4")

        then:
        result == []
    }
}
