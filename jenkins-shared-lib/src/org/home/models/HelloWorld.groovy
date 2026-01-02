package org.home.models

//import com.cloudbees.groovy.cps.NonCPS

class HelloWorld implements Serializable {

    private final def steps
    private String baseHelloString
    private String hello

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
        return formatHello()
    }
    HelloWorld setHello(String helloString) {
        this.hello = helloString
        return this
    }

    // ---- LOGICA PURA (NO CPS) ----
    @NonCPS
    private String formatHello() {
        return baseHelloString + hello
    }

    /*
    Cos’è CPS in Jenkins
            CPS = Continuation Passing Style
    In Jenkins Pipeline ogni step può fermare il job (echo, sh, input, sleep, ecc.).
    Jenkins quindi:
    * salva lo stato del programma
    * termina l’esecuzione
    * riprende da lì quando lo step finisce (o dopo un restart di Jenkins)

    Per farlo, Jenkins trasforma il tuo Groovy in una macchina a stati.
    */

}

