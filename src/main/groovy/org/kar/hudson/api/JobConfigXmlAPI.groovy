package org.kar.hudson.api

/**
 * @author Kelly Robinson
 */
class JobConfigXmlAPI
{
    private jsonSupport = new JSONRequestSupport()

    def loadJobConfig(rootUrl)
    {
        jsonSupport.loadJSON(rootUrl, 'config.xml')
    }

}

