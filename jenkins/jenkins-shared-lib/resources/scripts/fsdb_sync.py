import os
import sys
import mysql.connector
from mysql.connector import Error

def list_directory(path):
    print(f"Listing contents of: {path}")
    try:
        for item in os.listdir(path):
            print(item)
    except Exception as e:
        print(f"Errore nel listare la directory: {e}")
        sys.exit(1)

def connect_to_db(host, database):
    try:
        connection = mysql.connector.connect(
            host=host,
            database=database,
            user='readonly_user',
            password='readonly_pass'
        )
        if connection.is_connected():
            print(f"Connessione a {database}@{host} riuscita.")
            connection.close()
    except Error as e:
        print(f"Errore di connessione a MySQL: {e}")
        sys.exit(1)

if __name__ == '__main__':
    if len(sys.argv) != 4:
        print("Uso: task.py <path> <db_host> <db_name>")
        sys.exit(1)

    dir_path, db_host, db_name = sys.argv[1], sys.argv[2], sys.argv[3]
    list_directory(dir_path)
    connect_to_db(db_host, db_name)

