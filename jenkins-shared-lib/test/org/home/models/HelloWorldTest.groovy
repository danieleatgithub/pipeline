package org.home.models

import org.home.models.HelloWorld
import spock.lang.Specification

class HelloWorldTest extends Specification {

    def steps = Mock(Object)

    def "hello() deve concatenare la stringa base con il parametro passato"() {
        given:
        def service = new HelloWorld(steps, "Hello ")

        when:
        def result = service.hello("World")

        then:
        result == "Hello World"
    }

    def "hello() deve usare la stringa di default se non specificata nel costruttore"() {
        given:
        def service = new HelloWorld(steps)

        when:
        def result = service.hello("Daniele")

        then:
        result == "Hello Daniele"
    }
}
