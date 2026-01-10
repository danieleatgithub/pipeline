import org.home.steps.HelloWorldStep

def call(String base = "World") {
    return new HelloWorldStep(this, base)
}
