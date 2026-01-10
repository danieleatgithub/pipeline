package org.home.steps

import spock.lang.Specification

class HelloWorldStepTest extends Specification {

    def "say() should echo default hello world"() {
        given:
        def steps = new FakeSteps()
        def step = new HelloWorldStep(steps)

        when:
        def result = step.say()

        then:
        result == "Hello World"
        steps.logs == ["[HelloWorld] Hello World"]
    }

    def "say() should echo custom base"() {
        given:
        def steps = new FakeSteps()
        def step = new HelloWorldStep(steps, "Ciao ")

        when:
        def result = step.say()

        then:
        result == "Ciao World"
        steps.logs == ["[HelloWorld] Ciao World"]
    }

    def "setHello() should change hello value"() {
        given:
        def steps = new FakeSteps()
        def step = new HelloWorldStep(steps)

        when:
        step.setHello("Daniele")
        def result = step.say()

        then:
        result == "Hello Daniele"
        steps.logs == ["[HelloWorld] Hello Daniele"]
    }
}

