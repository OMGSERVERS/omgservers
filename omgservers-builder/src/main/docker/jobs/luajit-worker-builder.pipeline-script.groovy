pipeline {
    agent any
    parameters {
        string(name: "namespace", description: "Namespace within a registry.")
        string(name: "containerName", description: "Image name to build.")
        string(name: "versionId", description: "The version id is used as the docker image tag.")
        text(name: "sourceCodeJson", description: "Source code is in json representation.")
        string(name: "dockerRegistry", description: "Registry address to use in form of https://host:port.")
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
                    docker.withRegistry("${dockerRegistry}") {
                        def dockerImage = docker.build("${namespace}/${containerName}:${versionId}")
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