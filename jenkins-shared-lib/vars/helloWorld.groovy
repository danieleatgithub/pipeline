import org.home.models.HelloWorld

def call(String helloStringIn) {
    def HelloObject = new HelloWorld(this)
    def helloStringOut = HelloObject.hello(helloStringIn)
    return helloStringOut
}
