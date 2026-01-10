package org.home.models

class HelloWorld implements Serializable {

    private String baseHelloString
    private String hello

    HelloWorld(String helloString = "Hello ") {
        this.baseHelloString = helloString
        this.hello = "World"
    }

    String getHello() {
        return baseHelloString + hello
    }

    HelloWorld setHello(String helloString) {
        this.hello = helloString
        return this
    }
}
