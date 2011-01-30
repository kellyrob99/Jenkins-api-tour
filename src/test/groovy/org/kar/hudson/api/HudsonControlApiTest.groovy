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
        api.sendQuiet(rootUrl)

        then:
        true //what to do here to check?

        when:
        api.sendCancelQuiet(rootUrl)

        then:
        true //what to do here to check?
    }
}
