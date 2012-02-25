@GrabResolver(name='glassfish', root='http://maven.glassfish.org/content/groups/public/')
@GrabResolver(name="github", root="http://kellyrob99.github.com/Jenkins-api-tour/repository")
@Grab('org.kar:hudson-api:0.2-SNAPSHOT')
@GrabExclude('org.codehaus.groovy:groovy')
import static java.net.HttpURLConnection.*
import org.kar.hudson.api.*
import org.kar.hudson.api.cli.HudsonCliApi

String rootUrl = 'http://localhost:8080'
HudsonCliApi cliApi = new HudsonCliApi()

def findPluginsWithUpdates = '''
hudson.pluginManager.plugins.inject([]) { List toUpdate, plugin ->
    if(plugin.hasUpdate())
    {
        toUpdate << plugin.shortName
    }
    toUpdate
}.inspect()
'''
OutputStream updateablePlugins = new ByteArrayOutputStream()
cliApi.runCliCommand(rootUrl, ['groovysh', findPluginsWithUpdates], System.in, updateablePlugins, System.err)

def listOfPlugins = Eval.me(cliApi.parseResponse(updateablePlugins.toString()))
listOfPlugins.each{ plugin ->
    cliApi.runCliCommand(rootUrl, ['install-plugin', plugin])
}

//  Restart a node, required for newly installed plugins to be made available.
cliApi.runCliCommand(rootUrl, 'safe-restart')
