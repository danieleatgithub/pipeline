package org.home.models

class HelloWorld implements Serializable {

    private final def steps
    private final String baseHelloString

    HelloWorld(steps, String helloString = "Hello ") {
        this.steps = steps
        this.baseHelloString = helloString
    }

    String hello(String helloString) {
        steps.echo "[HelloWorld] helloString = ${helloString}"
        return this.baseHelloString + helloString
    }

}

