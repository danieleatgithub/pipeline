package org.home.steps

import org.home.models.HelloWorld

class HelloWorldStep implements Serializable {

    private final Object steps
    private final HelloWorld model

    HelloWorldStep(Object steps) {
        this(steps, "Hello ")
    }

    HelloWorldStep(Object steps, String base) {
        this.steps = steps
        this.model = new HelloWorld(base)
    }

    String say() {
        def msg = model.getHello()
        steps.echo "[HelloWorld] ${msg}"
        return msg
    }

    void setHello(String hello) {
        model.setHello(hello)
    }
}
