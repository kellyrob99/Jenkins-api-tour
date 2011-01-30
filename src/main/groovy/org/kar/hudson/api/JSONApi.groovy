package org.kar.hudson.api

import static org.kar.hudson.api.HudsonApiPaths.*

/**
 * @author Kelly Robinson
 */
class JSONApi
{
    private final GetRequestSupport requestSupport = new GetRequestSupport()

    /**
     * Display the json api for a particular Hudson url.
     * @param rootUrl the url to query for the hosted json api
     * @return  contents of api in json format
     */
    def inspectApi(String rootUrl)
    {
        requestSupport.get(rootUrl, API_JSON)
    }

    /**
     * Display the json api for a particular Hudson computer.
     * @param rootUrl the url of the Hudson server
     * @return  contents of computer api in json format
     */
    def inspectComputer(String rootUrl)
    {
        requestSupport.get(rootUrl, COMPUTER)
    }
}
