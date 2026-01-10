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

    String say() {
        String msg = model.hello
        steps.echo "[HelloWorld] ${msg}"
        return msg
    }

    void setHello(String hello) {
        model.setHello(hello)
    }
}
