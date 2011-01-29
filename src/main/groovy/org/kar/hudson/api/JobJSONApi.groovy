package org.kar.hudson.api

/**
 * @author Kelly Robinson
 */
class JobJSONApi
{
    static final LAST_SUCCESSFUL = 'lastSuccessfulBuild/api/json'
    static final JOB = 'api/json'
    static final TEST_REPORT = 'testReport/api/json'
    private jsonSupport = new JSONRequestSupport()

    def inspectSuccessfulJob(rootUrl)
    {
        jsonSupport.loadJSON(rootUrl, LAST_SUCCESSFUL)
    }

    def inspectJob(rootUrl)
    {
        jsonSupport.loadJSON(rootUrl, JOB, 1)
    }

    def inspectBuildTests(rootUrl)
    {
        jsonSupport.loadJSON(rootUrl, TEST_REPORT)
    }
}
