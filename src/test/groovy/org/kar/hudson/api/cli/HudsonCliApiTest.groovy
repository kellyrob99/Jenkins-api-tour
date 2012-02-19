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
    //final JSONApi jsonApi = new JSONApi()

    static final String UPDATE_ERROR = '''git is neither a valid file, URL, nor a plugin artifact name in the update center
No update center data is retrieved yet from: http://updates.jenkins-ci.org/update-center.json
git looks like a short plugin name. Did you mean 'null'?
'''

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
//        println commands.inspect()
        commands.size() > 0
    }

    def "should be able to run a groovy shell"()
    {
        final ByteArrayOutputStream output = new ByteArrayOutputStream()
        final message = 'hello from hudson-cli!'

        when:
        api.runCliCommand(rootUrl, ['groovysh', "println '$message'".toString()], System.in, output, System.err)

        then:
        output.toString().split('\n')[0] == message
    }

    /**
     * Expected output of 'who-am-i' command looks like:
     * ''Authenticated as: anonymous
     * Authorities:
     *   anonymous'''
     */
    def "should be able to figure out who-am-i"()
    {
        final ByteArrayOutputStream output = new ByteArrayOutputStream()
        when:
        api.runCliCommand(rootUrl,'who-am-i', System.in, output, System.err)

        
        then:
        def whoAmI = output.toString().split('\n')
        def authentication = whoAmI[0].split(':').collect {it.trim()}
        def authorization = whoAmI[1..-1].collect{it.trim()}

        authentication[1] == 'anonymous'
        authorization[1] == 'anonymous'
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
        map.hudsonHomeIsFull != null
        map.hudsonHomeIsFull.enabled == true
    }

    /**
     * This test is suffering from https://issues.jenkins-ci.org/browse/JENKINS-10061
     * This works for installing plugins, but ONLY after manually triggering an update check for plugins from within
     * the GUI. keeping the test intact to track when/if this bug is fixed.
     */
    def "should be able to install the git plugin"()
    {
        final ByteArrayOutputStream output = new ByteArrayOutputStream()
        when:
        //jsonApi.checkForUpdates(rootUrl)
        api.runCliCommand(rootUrl, ['install-plugin', 'git'], System.in, output, System.err)
        then:
        output.toString() == UPDATE_ERROR
    }
}
