package org.kar.hudson.api

import groovyx.net.http.HTTPBuilder

/**
 * @author Kelly Robinson
 */
class JSONRequestSupport
{
    private def loadJSON(rootUrl, path, depth = 0)
    {
        HTTPBuilder http = new HTTPBuilder(rootUrl)
        http.handler.failure = { resp ->
            println "Unexpected failure on $rootUrl$path: ${resp.statusLine} ${resp.status}"
        }

        def info
        http.get(path: path, query: [depth: depth]) { resp, json ->
            info = json
        }
        info
    }
}
