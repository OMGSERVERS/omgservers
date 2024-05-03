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
                    FROM ${env.DOCKER_REGISTRY}/omgservers/omgservers-luajit:1.0.0-SNAPSHOT                    
                    ADD lua /home/user/bin/lua
                    WORKDIR /home/user/bin/lua
                    CMD ["luajit", "main.lua"]
                """
                script {
                    docker.withRegistry("https://${env.DOCKER_REGISTRY}") {
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