pipeline {
    agent any
    parameters {
        string(name: "tenantId", description: "Customer tenant id is use as the namespace within a registry.")
        string(name: "stageId", description: "The stage id is used as the docker image name.")
        string(name: "versionId", description: "The version id is used as the docker image tag.")
        text(name: "sourceCodeJson", description: "Source code is in json representation.")
    }
    stages {
        stage("Prepare") {
            steps {
                cleanWs()
                dir("lua") {
                    script {
                        def files = readJSON text: "${sourceCodeJson}"
                        files.each { file ->
                            def fileName = file.file_name
                            def base64content = file.base64content
                            def decodedContent = new String(base64content.decodeBase64())

                            writeFile file: fileName, text: decodedContent
                            sh "cat ${fileName}"
                        }
                    }
                    sh "ls -lah"
                }
            }
        }
        stage("Build") {
            steps {
                writeFile file: 'Dockerfile', text: """
                    FROM nickblah/luajit:2.1.0-beta3-luarocks-debian
                    RUN apt-get update && apt-get install -y build-essential
                    RUN luarocks install luasocket
                    RUN luarocks install lua-cjson
                    RUN luarocks install inspect
                    RUN luarocks install base64
                """
                script {
                    def dockerImage = docker.build("omgservers/${tenantId}/${stageId}:${versionId}")
                }
            }
        }
    }
}