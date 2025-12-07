import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def call(String jobPath) {
    // Percorsi relativi alla workspace
    String globalPath = "${env.WORKSPACE}/jenkins/jenkins-shared-config/global-config.json"
    String projectPath = "${jobPath}/project-config.json"

    def globalCfg = [:]
    def globalFile = new File(globalPath)
    if (globalFile.exists()) {
        globalCfg = new JsonSlurper().parse(globalFile)
    }

    def projectCfg = [:]
    def projectFile = new File(projectPath)
    if (projectFile.exists()) {
        projectCfg = new JsonSlurper().parse(projectFile)
    }

    // Merge: project override global
    def merged = mergeMaps(globalCfg, projectCfg)

    // Ritorna la mappa
    return merged
}

// Merger ricorsivo
def mergeMaps(Map base, Map override) {
    Map result = [:]
    result.putAll(base)

    override.each { key, value ->
        if (value instanceof Map && base[key] instanceof Map) {
            result[key] = mergeMaps(base[key], value)
        } else {
            result[key] = value
        }
    }
    return result
}
