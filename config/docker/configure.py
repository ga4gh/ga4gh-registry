import os

ENV_VARS = [
    "SERVERCONTEXTPATH",
    "SERVERPORT",
    "DBHOST",
    "DBPORT",
    "DBNAME",
    "DBSCHEMA",
    "DBUSER",
    "DBPASSWORD",
    "USESSL"
]

DIR = "./config/docker/"
OUTPUT_FILE = DIR + "application.properties"
INPUT_FILE = OUTPUT_FILE + ".template"

def main():

    content = open(INPUT_FILE, "r").read()

    for env_var in ENV_VARS:
        content = content.replace("VAR_" + env_var, os.environ[env_var])

    open(OUTPUT_FILE, "w").write(content)

if __name__ == '__main__':
    main()