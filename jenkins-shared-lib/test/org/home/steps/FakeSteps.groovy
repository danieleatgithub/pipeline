package org.home.steps

class FakeSteps {

    List<String> logs = []

    void echo(Object msg) {
        logs << msg.toString()
    }
}

