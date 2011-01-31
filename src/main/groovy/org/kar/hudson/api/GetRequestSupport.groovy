package org.kar.hudson.api

import groovyx.net.http.HTTPBuilder

/**
 * @author Kelly Robinson
 */
class GetRequestSupport
{
    /**
     * Load info from a particular rootUrl+path, optionally specifying a 'depth' query
     * parameter(default depth = 0)
     *
     * @param rootUrl the base url to access
     * @param path  the api path to append to the rootUrl
     * @param depth the depth query parameter to send to the api, defaults to 0
     * @return parsed json(as a map) or xml(as GPathResult)
     */
    def get(String rootUrl, String path, int depth = 0)
    {
        def status
        HTTPBuilder http = new HTTPBuilder(rootUrl)
        http.handler.failure = { resp ->
            println "Unexpected failure on $rootUrl$path: ${resp.statusLine} ${resp.status}"
            status = resp.status
        }

        def info
        http.get(path: path, query: [depth: depth]) { resp, json ->
            info = json
            status = resp.status
        }
        info ?: status
    }
}
