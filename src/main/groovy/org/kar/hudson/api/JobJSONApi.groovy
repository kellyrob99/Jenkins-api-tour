package org.kar.hudson.api

import groovyx.net.http.HTTPBuilder

/**
 * @author Kelly Robinson
 */
class JobJSONApi
{
    static final LAST_SUCCESSFUL = 'lastSuccessfulBuild/api/json'
    static final JOB = 'api/json'
    static final TEST_REPORT = 'testReport/api/json'

    def inspectSuccessfulJob(rootUrl)
    {
        loadJSON(rootUrl, LAST_SUCCESSFUL)
    }

    def inspectJob(rootUrl)
    {
        loadJSON(rootUrl, JOB)
    }

    def inspectBuildTests(rootUrl)
    {
        loadJSON(rootUrl, TEST_REPORT)
    }

    private def loadJSON(rootUrl, path)
    {
        HTTPBuilder http = new HTTPBuilder(rootUrl)
        http.handler.failure = { resp ->
            println "Unexpected failure on $rootUrl$path: ${resp.statusLine} ${resp.status}"
        }

        def info
        http.get(path: path) { resp, json ->
            info = json
        }
        info
    }

}
