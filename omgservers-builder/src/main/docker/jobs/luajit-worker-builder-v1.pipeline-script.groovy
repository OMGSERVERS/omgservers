pipeline {
    agent any
    parameters {
        string(name: "groupId", description: "Group for new container.")
        string(name: "containerName", description: "Image name to build.")
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
                writeFile file: "Dockerfile", text: """
                    FROM nickblah/luajit:2.1.0-beta3-luarocks-debian
                    RUN apt-get update && apt-get install -y build-essential
                    RUN luarocks install luasocket
                    RUN luarocks install lua-cjson
                    RUN luarocks install inspect
                    RUN luarocks install base64
                    ADD lua /home/user/bin/lua
                    WORKDIR /home/user/bin/lua
                    CMD ["luajit", "main.lua"]
                """
                script {
                    docker.withRegistry("${env.DOCKER_REGISTRY}") {
                        def dockerImage = docker.build("${groupId}/${containerName}:${versionId}")
                        dockerImage.push()
                        def imageName = dockerImage.imageName()
                        writeFile file: "image", text: imageName
                    }
                }
            }
        }
    }
    post {
        success {
            archiveArtifacts artifacts: "image", allowEmptyArchive: true
        }
    }
}