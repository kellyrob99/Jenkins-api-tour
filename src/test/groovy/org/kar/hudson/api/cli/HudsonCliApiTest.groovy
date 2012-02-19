package org.kar.hudson.api.cli

import spock.lang.Specification

/**
 * @author Kelly Robinson
 */
@SuppressWarnings('MethodName')
class HudsonCliApiTest extends Specification
{
    def rootUrl = 'http://localhost:8080/'

    final HudsonCliApi api = new HudsonCliApi()

    def "should be able to download the hudson cli jar"()
    {
        when:
        api.downloadJar(rootUrl)

        then:
        new File("${System.getProperty('user.home')}/${HudsonCliApi.FINAL_PATH}").exists()
    }

    def "should be able to load commands from hudson-cli"()
    {
        final ByteArrayOutputStream output = new ByteArrayOutputStream()
        when:
        //output of help command is written to err stream
        api.runCliCommand(rootUrl, 'help', System.in, System.out, output)

        then:
        final String[] lines = output.toString().split('\n')
        def commands = [:]
        for (int i = 0; i < (lines.size() - 1); i++)
        {
            if (i % 2 == 0)
            {
                commands[lines[i]] = lines[i + 1]
            }
        }
        println commands
        commands.size() > 0
    }

    def "should be able to run a groovy shell"()
    {
        final ByteArrayOutputStream output = new ByteArrayOutputStream()
        final message = 'hello from hudson-cli!'

        when:
        api.runCliCommand(rootUrl, ['groovysh', "println '$message'".toString()], System.in, output, System.err)

        then:
        println output.toString()
        output.toString().split('\n')[0] == message
    }


    def "should be able to query hudson object through a groovy script"()
    {
        final ByteArrayOutputStream output = new ByteArrayOutputStream()
        when:
        api.runCliCommand(rootUrl, ['groovysh', '''
hudson.model.Hudson.instance.administrativeMonitors.inject([:]){ result, it ->
    result[it.id] = [enabled: it.enabled, activated: it.activated]
    result
}.inspect()
'''],
                System.in, output, System.err)


        then:
        def s = output.toString()[11..-1]   // the output string is wrapped in some boilerplate, remove it
        println s
        //some keys in the map contain $ signs, so we opt to just replace them with themselves
        def map = new GroovyShell(new Binding('CoreUpdateMonitor':'$CoreUpdateMonitor',
                'AdministrativeMonitorImpl':'$AdministrativeMonitorImpl')).evaluate(s)
        println map
        println map.getClass()

//        output.toString().split('\n')[0].startsWith('job')
    }

    def "should be able to install the git plugin"()
    {
        final ByteArrayOutputStream output = new ByteArrayOutputStream()
        when:
        api.runCliCommand(rootUrl, ['install-plugin', 'git'], System.in, output, System.err)
        then:
        println output.toString()
        true
    }
}
