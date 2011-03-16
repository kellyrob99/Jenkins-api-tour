package org.kar.hudson.api

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Depends on having Hudson running on the local machine.
 *
 * @author Kelly Robinson
 */
@SuppressWarnings('MethodName')
class UDPApiTest extends Specification
{
    final static UDP_RESPONSE_PATTERN = '<hudson><version>.*</version><slave-port>.*</slave-port></hudson>'

    @Unroll("querying of #rootUrl should match #xmlResponse")
    def "should be able to test for presence of Hudson by sending a UDP packet"()
    {
        def udpInfo = new UDPApi()

        when:
        def response = udpInfo.sendUDP(rootUrl)

        then:
        response ==~ xmlResponse
        println response

        where:
        rootUrl | xmlResponse
        '127.0.0.1' | UDP_RESPONSE_PATTERN
        'localhost' | UDP_RESPONSE_PATTERN
    }
}
