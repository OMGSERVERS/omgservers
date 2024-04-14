// https://jenkinsci.github.io/job-dsl-plugin/

pipelineScript = new File('/var/jobs/luajit-worker-builder.pipeline-script.groovy').getText("UTF-8")
pipelineJob("luajit-worker-builder") {
    description("Job builds LuaJIT workers")

    parameters {
        stringParam("namespace", null, "Namespace within a registry.")
        stringParam("containerName", null, "Image name to build.")
        stringParam("versionId", null, "The version id is used as the docker image tag.")
        textParam("sourceCodeJson", null, "Source code is in json representation.")
        stringParam("dockerRegistry", null, "Registry address to use in form of https://host:port.")
    }

    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}
