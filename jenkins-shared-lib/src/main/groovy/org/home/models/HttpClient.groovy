package org.home.models

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class HttpClient implements Serializable {

    private final def steps
    private final String baseUrl

    HttpClient(steps, String baseUrl) {
        this.steps = steps
        this.baseUrl = baseUrl
    }

    Map get(String path, Map headers = [:]) {
        return request('GET', path, [:], headers)
    }

    Map post(String path, Map body = [:], Map headers = [:]) {
        return request('POST', path, body, headers)
    }

    private Map request(String method, String path, Map body, Map headers) {

        def response = steps.httpRequest(
            httpMode: method,
            url: "${baseUrl}${path}",
            contentType: method == 'POST' ? 'APPLICATION_JSON' : null,
            requestBody: body ? JsonOutput.toJson(body) : null,
            customHeaders: toHeaders(headers),
            validResponseCodes: '100:599',
            consoleLogResponseBody: true
        )

        Map<String, Object> result = [
                status  : response.status,
                headers : response.headers ?: [:],
                body    : [:]
        ]

        if (response.content) {
            result.body = new JsonSlurper().parseText(response.content) as Map
        }

        return result
    }

    private static List<Map> toHeaders(Map headers) {
        headers.collect { k, v ->
            [name: k.toString(), value: v.toString()]
        }
    }
}
