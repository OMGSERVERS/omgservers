pipelineScript = new File('/var/jobs/luajit-worker-builder.pipeline-script.groovy').getText("UTF-8")
pipelineJob("luajit-worker-builder") {
    description("Job builds LuaJIT workers")

    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}
