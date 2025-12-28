import org.home.lenovosrv.models.LenovosrvServiceFactory

def call(Map args = [:]) {

    String baseUrl     = args.baseUrl   ?: error("baseUrl obbligatorio")
    String action      = args.action    ?: error("action obbligatoria")
    String mountTarget = args.mountTarget
    String fstype      = args.fstype

    def factory = new LenovosrvServiceFactory(
        this,
        baseUrl
    )

    def mountSvc = factory.mountService()

    switch(action) {

        case 'status':
            return mountSvc.status()

        case 'mount':
            if (!mountTarget) error "mountTarget obbligatorio per mount"
            return mountSvc.mount(mountTarget)

        case 'umount':
            if (!mountTarget) error "mountTarget obbligatorio per umount"
            return mountSvc.umount(mountTarget)

        case 'listByFsType':
            if (!fstype) error "fstype obbligatorio per listByFsType"
            return mountSvc.getMountsByFsType(fstype)

        default:
            error "action non supportata: ${action}"
    }
}
