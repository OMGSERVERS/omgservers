// https://jenkinsci.github.io/job-dsl-plugin/

pipelineScript = new File('/var/jobs/luajit-worker-builder-v1.pipeline-script.groovy').getText("UTF-8")
pipelineJob("luajit-worker-builder-v1") {
    description("Job builds LuaJIT workers, v1")

    parameters {
        stringParam("groupId", null, "Group for new container.")
        stringParam("containerName", null, "Image name to build.")
        stringParam("versionId", null, "The version id is used as the docker image tag.")
        textParam("base64Archive", null, "Base64 encoded version archive.")
    }

    quietPeriod(0)

    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}
