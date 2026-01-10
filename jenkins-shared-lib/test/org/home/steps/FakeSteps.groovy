package org.home.steps

class FakeSteps {
    List<String> logs = []

    void echo(String msg) {
        logs << msg
    }
}


