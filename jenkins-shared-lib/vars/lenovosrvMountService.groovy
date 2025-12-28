def call(Map args = [:]) {
    new org.home.lenovosrv.pipeline.LenovosrvMountService(this).run(args)
}
