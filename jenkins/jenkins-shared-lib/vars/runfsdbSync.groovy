def call(String path) {
    echo "Eseguo script Python su path: ${path}"

    // Salva lo script in workspace
    writeFile file: 'task.py', text: libraryResource('scripts/task.py')

    sh """
        python3 task.py '${path}' '${env.DB_HOST}' '${env.DB_NAME}'
    """
}
