package org.home.lenovosrv.pipeline

import org.home.lenovosrv.models.LenovosrvServiceFactory

class LenovosrvMountService {

    def script

    LenovosrvMountService(script) {
        this.script = script
    }

    def run(Map args = [:]) {

        String baseUrl     = args.baseUrl   ?: script.error("baseUrl obbligatorio")
        String action      = args.action    ?: script.error("action obbligatoria")
        String mountTarget = args.mountTarget
        String fstype      = args.fstype

        def factory = new LenovosrvServiceFactory(script, baseUrl)
        def mountSvc = factory.mountService()

        switch(action) {

            case 'status':
                return mountSvc.status()

            case 'mount':
                if (!mountTarget) script.error "mountTarget obbligatorio per mount"
                return mountSvc.mount(mountTarget)

            case 'umount':
                if (!mountTarget) script.error "mountTarget obbligatorio per umount"
                return mountSvc.umount(mountTarget)

            case 'listByFsType':
                if (!fstype) script.error "fstype obbligatorio per listByFsType"
                return mountSvc.getMountsByFsType(fstype)

            default:
                script.error "action non supportata: ${action}"
        }
    }
}
