package org.home.models

class HelloWorld implements Serializable {

    private final def steps
    private final String baseHelloString

    HelloWorld(steps, String helloString = "Hello ") {
        this.steps = steps
        this.baseHelloString = helloString
        this.hello = "World"
    }

    HelloWorld sayHello() {
        steps.echo "[HelloWorld] sayHello " + this.baseHelloString +  this.hello
        return this
    }
    String getHello() {
        steps.echo "[HelloWorld] getHello"
        return this.baseHelloString + this.hello
    }
    HelloWorld setHello(String helloString) {
        this.hello = helloString
        return this
    }

}

