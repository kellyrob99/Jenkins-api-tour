package org.kar.hudson.api

import spock.lang.Specification

/**
 * @author Kelly Robinson
 */
class HudsonControlApiTest extends Specification
{
    def rootUrl = 'http://localhost:8080/'
    private final HudsonControlApi api = new HudsonControlApi()

    def "test quieting and waking"()
    {
        when:
        def response = api.sendQuiet(rootUrl)

        then:
        response

        when:
        response = api.sendCancelQuiet(rootUrl)

        then:
        response
    }

    //does nothing on mac
    def "test restart"()
    {
        when:
        def response = api.sendSafeRestart(rootUrl)

        then:
        response

        when:
        response = api.sendRestart(rootUrl)

        then:
        response
    }
}
