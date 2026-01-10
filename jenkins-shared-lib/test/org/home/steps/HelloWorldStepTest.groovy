package org.home.steps

import spock.lang.Specification

class HelloWorldStepTest extends Specification {

    def "say() should echo default hello world and return this"() {
        given:
        def steps = new FakeSteps()
        def step = new HelloWorldStep(steps)

        when:
        def result = step.say()

        then:
        result.is(step)
        steps.logs.size() == 1
        steps.logs[0] == "[HelloWorld] Hello World"
    }

    def "setHello() should change hello message"() {
        given:
        def steps = new FakeSteps()
        def step = new HelloWorldStep(steps)

        when:
        step.setHello("Spock")

        then:
        step.hello == "Hello Spock"
    }

    def "say() should echo custom hello"() {
        given:
        def steps = new FakeSteps()
        def step = new HelloWorldStep(steps)
        step.setHello("Groovy")

        when:
        step.say()

        then:
        steps.logs[0] == "[HelloWorld] Hello Groovy"
    }

    def "fluent api should work"() {
        given:
        def steps = new FakeSteps()
        def step = new HelloWorldStep(steps)

        when:
        step.setHello("Chain").say()

        then:
        step.hello == "Hello Chain"
        steps.logs[0] == "[HelloWorld] Hello Chain"
    }
}
