package org.home.steps

import org.home.models.HelloWorld

class HelloWorldStep implements Serializable {

    private final def steps
    private final HelloWorld model

    HelloWorldStep(steps) {
        this(steps, "Hello ")
    }

    HelloWorldStep(steps, String base) {
        this.steps = steps
        this.model = new HelloWorld(base)
    }

    HelloWorldStep say() {
        String msg = model.hello
        steps.echo "[HelloWorld] ${msg}"
        return this
    }

    HelloWorldStep setHello(String hello) {
        model.setHello(hello)
        return this
    }

    String getHello() {
        return model.getHello()
    }
}
