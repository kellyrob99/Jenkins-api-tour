package org.kar.hudson.api

import groovyx.net.http.HTTPBuilder

/**
 * @author Kelly Robinson
 */
class JSONApi
{
    private jsonSupport = new JSONRequestSupport()

    def inspectApi(rootUrl)
    {
        jsonSupport.loadJSON(rootUrl, 'api/json')
    }

    def inspectComputer(rootUrl)
    {
        jsonSupport.loadJSON(rootUrl, 'computer/api/json')
    }
}
