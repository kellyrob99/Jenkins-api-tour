package org.kar.hudson.api

import groovyx.net.http.HTTPBuilder

/**
 * @author Kelly Robinson
 */
class JSONApi
{
    def inspectApi(rootUrl)
    {
        HTTPBuilder http = new HTTPBuilder(rootUrl)

        def hudsonInfo
        http.get(path: 'api/json') { resp, json ->
            hudsonInfo = json
        }
        hudsonInfo
    }

    def inspectComputer(rootUrl)
    {
        HTTPBuilder http = new HTTPBuilder(rootUrl)

        def computer
        http.get(path: 'computer/api/json') { resp, json ->
            computer = json
        }
        computer
    }
}
