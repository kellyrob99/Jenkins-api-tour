package org.kar.hudson.api

import groovyx.net.http.*

/**
 * @author Kelly Robinson
 */
class PostRequestSupport
{
    /**
     *
     * @param rootUrl
     * @param path
     * @param postBody
     * @param contentType
     * @return
     */
    def post(String rootUrl, String path, postBody= [:], params = [:],  ContentType contentType = ContentType.URLENC)
    {
        HTTPBuilder http = new HTTPBuilder(rootUrl)
        def status
        http.handler.failure = { resp ->
            println "Unexpected failure on $rootUrl$path: ${resp.statusLine} ${resp.status}"
            status = resp.statusLine.statusCode
        }

        http.post(path: path, body: postBody, query: params,
                requestContentType: contentType) { resp ->
            assert resp.statusLine.statusCode < 400
            status = resp.statusLine.statusCode
        }
        status
    }
}
