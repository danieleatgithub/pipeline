import org.home.models.HelloWorld

def call(String base = "World") {
    return new HelloWorld(this, base)
}
