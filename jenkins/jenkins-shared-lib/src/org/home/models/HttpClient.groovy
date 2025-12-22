package org.home.models

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class HttpClient implements Serializable {

    private final def steps
    private final String baseUrl
    private final JsonSlurper jsonSlurper = new JsonSlurper()

    HttpClient(steps, String baseUrl) {
        this.steps = steps
        this.baseUrl = baseUrl
    }

    Map get(String path, Map headers = [:]) {
        return request(
            method: 'GET',
            path: path,
            headers: headers
        )
    }

    Map post(String path, Map body = [:], Map headers = [:]) {
        return request(
            method: 'POST',
            path: path,
            body: body,
            headers: headers
        )
    }

    private Map request(Map args) {
        def response = steps.httpRequest(
            httpMode: args.method,
            url: "${baseUrl}${args.path}",
            contentType: args.method == 'POST' ? 'APPLICATION_JSON' : null,
            requestBody: args.body ? JsonOutput.toJson(args.body) : null,
            customHeaders: toHeaders(args.headers),
            validResponseCodes: '200,500'
        )

        return response?.content
            ? jsonSlurper.parseText(response.content)
            : [:]
    }

    private static List<Map> toHeaders(Map headers) {
        headers.collect { k, v ->
            [name: k.toString(), value: v.toString()]
        }
    }
}
