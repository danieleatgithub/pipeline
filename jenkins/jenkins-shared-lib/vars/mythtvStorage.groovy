def call(Map args = [:]) {

    def baseUrl   = args.baseUrl   ?: error("baseUrl obbligatorio")
    def action    = args.action    ?: error("action obbligatoria")
    def groupName = args.groupName
    def hostName  = args.hostName
    def dirName   = args.dirName

    def factory = new org.home.mythtv.models.MythServiceFactory(
        this,
        baseUrl
    )

    def storageSvc = factory.storageService()

    // TODO Valutare di ritornare sempre storageSvc ed spostare le action come
    // metodi dell'oggetto
    switch (action) {

        case 'list':
            return storageSvc.getStorageGroupList(groupName, hostName)

        case 'add':
            if (!dirName || !groupName || !hostName) {
                error "dirName, groupName e hostName sono obbligatori per add"
            }
            return storageSvc.addStorageGroupDir(dirName, groupName, hostName)

        case 'remove':
            if (!dirName || !groupName || !hostName) {
                error "dirName, groupName e hostName sono obbligatori per remove"
            }
            return storageSvc.removeStorageGroupDir(dirName, groupName, hostName)

        default:
            error "action non supportata: ${action}"
    }
}
