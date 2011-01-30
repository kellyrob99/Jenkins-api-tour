package org.kar.hudson.api

import static org.kar.hudson.api.HudsonApiPaths.*

/**
 * @author Kelly Robinson
 */
class HudsonControlApi
{
    private final GetRequestSupport requestSupport = new GetRequestSupport()

    def sendQuiet(String rootUrl)
    {
        requestSupport.get(rootUrl, QUIET)
    }

    def sendCancelQuiet(String rootUrl)
    {
        requestSupport.get(rootUrl, CANCEL_QUIET)
    }

    def sendRestart(String rootUrl)
    {
        requestSupport.get(rootUrl, RESTART)
    }

    def sendSafeRestart(String rootUrl)
    {
        requestSupport.get(rootUrl, SAFE_RESTART)
    }
}
