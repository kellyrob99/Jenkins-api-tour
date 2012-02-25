@GrabResolver(name = 'glassfish', root = 'http://maven.glassfish.org/content/groups/public/')
@GrabResolver(name = "github", root = "http://kellyrob99.github.com/Jenkins-api-tour/repository")
@Grab('org.kar:hudson-api:0.2-SNAPSHOT')
@GrabExclude('org.codehaus.groovy:groovy')
import org.kar.hudson.api.cli.HudsonCliApi
/**
 * Open an interactive Groovy shell that imports the hudson.model.* classes and exposes
 * a 'hudson' and/or 'jenkins' object in the Binding which is an instance of hudson.model.Hudson
 */
HudsonCliApi cliApi = new HudsonCliApi()
String rootUrl = args ? args[0] :'http://localhost:8080'
cliApi.runCliCommand(rootUrl, 'groovysh')

