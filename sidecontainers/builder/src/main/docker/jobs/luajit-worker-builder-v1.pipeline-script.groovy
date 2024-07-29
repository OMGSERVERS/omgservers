pipeline {
    agent any
    parameters {
        string(name: "groupId", description: "Group for new container.")
        string(name: "containerName", description: "Image name to build.")
        string(name: "versionId", description: "The version id is used as the docker image tag.")
        text(name: "base64Archive", description: "Base64 encoded version archive.")
    }
    stages {
        stage("Prepare") {
            steps {
                cleanWs()
                script {
                    writeFile file: "archive.zip", text: base64Archive, encoding: "Base64"
                    unzip zipFile: "archive.zip", dir: "lua"
                }
            }
        }
        stage("Build") {
            steps {
                writeFile file: "Dockerfile", text: """
                    FROM omgservers/luajit:1.0.0-SNAPSHOT                    
                    ADD lua /home/user/bin/lua
                    WORKDIR /home/user/bin/lua
                    CMD ["luajit", "main.lua"]
                """
                script {
                    docker.withRegistry("https://${env.DOCKER_REGISTRY}", "builder-user-credentials") {
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