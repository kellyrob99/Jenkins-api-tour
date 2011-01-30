package org.kar.hudson.api

/**
 * @author Kelly Robinson
 */
class JobConfigXmlAPI
{
    private final GetRequestSupport requestSupport = new GetRequestSupport()

    /**
     * Load the configuration information for a Hudson job.
     *
     * @param rootUrl the base url of a Hudson job
     * @return the config.xml file(as a GPathResult)
     */
    def loadJobConfig(String rootUrl)
    {
        requestSupport.get(rootUrl, 'config.xml')
    }
}

