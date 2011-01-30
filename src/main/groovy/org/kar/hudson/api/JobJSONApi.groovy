package org.kar.hudson.api

import groovyx.net.http.ContentType
import static org.kar.hudson.api.HudsonApiPaths.*

/**
 * @author Kelly Robinson
 */
class JobJSONApi
{
    private final GetRequestSupport requestSupport = new GetRequestSupport()
    private final PostRequestSupport postSupport = new PostRequestSupport()

    /**
     * Display the information for the last successful build of a Hudson job.
     * @param rootUrl the url for a particular build
     * @return lastSuccessfulBuild in json format
     */
    def inspectSuccessfulJob(String rootUrl)
    {
        requestSupport.get(rootUrl, LAST_SUCCESSFUL)
    }

    /**
     * Display the job api for a particular Hudson job.
     * @param rootUrl the url for a particular build
     * @return job info in json format
     */
    def inspectJob(String rootUrl)
    {
        requestSupport.get(rootUrl, API_JSON)
    }

    /**
     * Display the test results for a particular Hudson job.
     * @param rootUrl the url for a particular build
     * @return test info in json format
     */
    def inspectBuildTests(String rootUrl)
    {
        requestSupport.get(rootUrl, TEST_REPORT)
    }

    /**
     * Trigger a build for a particular Hudson job.
     * @param rootUrl the url for a particular build
     * @return
     */
    def triggerBuild(String rootUrl)
    {
        requestSupport.get(rootUrl, BUILD)
    }

    def createJob(String rootUrl, String configXml, String jobName)
    {
        postSupport.post(rootUrl, CREATE_ITEM, configXml, [name:jobName], ContentType.XML)
    }

    /**
     *
     * @param rootUrl
     * @param params containing name=NEWJOBNAME from=FROMJOBNAME
     * @return
     */
    def copyJob(String rootUrl, Map params)
    {
        postSupport.post(rootUrl, CREATE_ITEM, params)
    }

    def deleteJob(String rootUrl)
    {
        postSupport.post(rootUrl, DELETE_JOB)
    }
}
