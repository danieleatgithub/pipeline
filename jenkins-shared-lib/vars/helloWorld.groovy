import org.home.models.HelloWorld

def call(String name = "World") {
    def hw = new HelloWorld(this)
    echo hw.hello(name)
}
