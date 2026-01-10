package org.home.models

import spock.lang.Specification
import spock.lang.Unroll

class HelloWorldTest extends Specification {

    def "should return default hello string"() {
        given:
        def helloWorld = new HelloWorld()

        expect:
        helloWorld.getHello() == "Hello World"
    }

    def "should change hello value using setter"() {
        given:
        def helloWorld = new HelloWorld()

        when:
        helloWorld.setHello("Groovy")

        then:
        helloWorld.getHello() == "Hello Groovy"
    }

    def "should support fluent API"() {
        given:
        def helloWorld = new HelloWorld()

        when:
        def result = helloWorld
                .setHello("Spock")

        then:
        result.is(helloWorld)
        helloWorld.getHello() == "Hello Spock"
    }

    @Unroll
    def "should support custom baseHelloString=#baseHello"() {
        given:
        def helloWorld = new HelloWorld(baseHello)

        expect:
        helloWorld.getHello() == baseHello + "World"

        where:
        baseHello << ["Ciao ", "Hola ", "Bonjour "]
    }

    def "should be serializable"() {
        expect:
        new HelloWorld() instanceof Serializable
    }
}
