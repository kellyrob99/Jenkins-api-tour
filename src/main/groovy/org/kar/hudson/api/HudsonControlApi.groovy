package org.kar.hudson.api

import static org.kar.hudson.api.HudsonApiPaths.*

/**
 * @author Kelly Robinson
 */
class HudsonControlApi
{
    private final GetRequestSupport requestSupport = new GetRequestSupport()

    /**
     * Instruct Hudson to get ready to shut down.
     *
     * @param rootUrl the url of the Hudson server
     * @return
     */
    def sendQuiet(String rootUrl)
    {
        requestSupport.get(rootUrl, QUIET)
    }

    /**
     * Instruct Hudson to get ready to work.
     *
     * @param rootUrl the url of the Hudson server
     * @return
     */
    def sendCancelQuiet(String rootUrl)
    {
        requestSupport.get(rootUrl, CANCEL_QUIET)
    }

    /**
     * Instruct Hudson to restart.
     *
     * @param rootUrl the url of the Hudson server
     * @return
     */
    def sendRestart(String rootUrl)
    {
        requestSupport.get(rootUrl, RESTART)
    }

    /**
     * Instruct Hudson to restart after builds are finished.
     *
     * @param rootUrl the url of the Hudson server
     * @return
     */
    def sendSafeRestart(String rootUrl)
    {
        requestSupport.get(rootUrl, SAFE_RESTART)
    }
}
