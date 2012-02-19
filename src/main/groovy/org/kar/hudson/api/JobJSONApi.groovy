package org.kar.hudson.api

import static org.kar.hudson.api.HudsonApiPaths.*
import groovyx.net.http.ContentType

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
    def inspectSuccessfulJob(String rootUrl, int depth = 0)
    {
        requestSupport.get(rootUrl, LAST_SUCCESSFUL, depth)
    }

    /**
     * Display the job api for a particular Hudson job.
     * @param rootUrl the url for a particular build
     * @return job info in json format
     */
    def inspectJob(String rootUrl, int depth = 0)
    {
        requestSupport.get(rootUrl, API_JSON, depth)
    }

    /**
     * Display the test results for a particular Hudson job.
     * @param rootUrl the url for a particular build
     * @return test info in json format
     */
    def inspectBuildTests(String rootUrl, int depth = 0)
    {
        requestSupport.get(rootUrl, TEST_REPORT, depth)
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

    /**
     * Create a new Job.
     * @param rootUrl  the url of the Hudson server
     * @param configXml the content of a config.xml file describing the new build
     * @param jobName  name of the new job
     * @return
     */
    @SuppressWarnings('FactoryMethodName')
    def createJob(String rootUrl, String configXml, String jobName)
    {
        postSupport.post(rootUrl, CREATE_ITEM, configXml, [name:jobName], ContentType.XML)
    }

    /**
     * Copy a job.
     * @param rootUrl the url of the Hudson server
     * @param params containing name=NEWJOBNAME&mode=copy&from=FROMJOBNAME
     * @return
     */
    def copyJob(String rootUrl, Map params)
    {
        postSupport.post(rootUrl, CREATE_ITEM, params)
    }

    /**
     * Delete a job.
     * @param rootUrl the url of the job
     * @return
     */
    def deleteJob(String rootUrl)
    {
        postSupport.post(rootUrl, DELETE_JOB)
    }
}
