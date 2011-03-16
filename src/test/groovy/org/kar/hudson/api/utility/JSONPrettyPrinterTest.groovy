package org.kar.hudson.api.utility

import spock.lang.Specification

/**
 * @author Kelly Robinson
 */
@SuppressWarnings('MethodName')
class JSONPrettyPrinterTest extends Specification
{
    def jsonPrinter = new JSONPrettyPrinter()

    static final String COMPUTER_INFO = '{"busyExecutors":0,"computer":[{"actions":[],"displayName":"master","executors":[{},{}],"icon":"computer.png","idle":true,"jnlpAgent":false,"launchSupported":true,"loadStatistics":{},"manualLaunchAllowed":true,"monitorData":{"hudson.node_monitors.SwapSpaceMonitor":null,"hudson.node_monitors.ArchitectureMonitor":"Mac OS X (x86_64)","hudson.node_monitors.TemporarySpaceMonitor":{"size":58392846336},"hudson.node_monitors.ResponseTimeMonitor":{"average":111},"hudson.node_monitors.DiskSpaceMonitor":{"size":58392846336},"hudson.node_monitors.ClockMonitor":{"diff":0}},"numExecutors":2,"offline":false,"offlineCause":null,"oneOffExecutors":[],"temporarilyOffline":false}],"displayName":"nodes","totalExecutors":2}'
    static final String ROOT_API = '{"assignedLabels":[{}],"mode":"NORMAL","nodeDescription":"the master Hudson node","nodeName":"","numExecutors":2,"description":"Welcome to my Test Hudson System","jobs":[{"name":"gradle","url":"http://localhost:8080/job/gradle/","color":"disabled"},{"name":"gradle-no-sonar","url":"http://localhost:8080/job/gradle-no-sonar/","color":"disabled"},{"name":"hudson-api","url":"http://localhost:8080/job/hudson-api/","color":"aborted"},{"name":"test","url":"http://localhost:8080/job/test/","color":"disabled"}],"overallLoad":{},"primaryView":{"name":"All","url":"http://localhost:8080/"},"slaveAgentPort":0,"useCrumbs":false,"useSecurity":false,"views":[{"name":"All","url":"http://localhost:8080/"}]}'

    def "should pretty print JSON if ruby program is installed"()
    {
        when:
        def prettyPrinted = jsonPrinter.prettyPrint('{ "foo": "bar" }')

        then:
        prettyPrinted == '''{
  "foo": "bar"
}
'''
    }

    def "format a bunch of canned json"()
    {
        when:
        println jsonPrinter.prettyPrint(COMPUTER_INFO)
        println jsonPrinter.prettyPrint(ROOT_API)

        then:
        true
    }
}
