@GrabResolver(name = 'glassfish', root = 'http://maven.glassfish.org/content/groups/public/')
@GrabResolver(name = "github", root = "http://kellyrob99.github.com/Jenkins-api-tour/repository")
@Grab('org.kar:hudson-api:0.2-SNAPSHOT')
@GrabExclude('org.codehaus.groovy:groovy')
import static java.net.HttpURLConnection.*
import org.kar.hudson.api.*
import org.kar.hudson.api.cli.HudsonCliApi

String rootUrl = 'http://localhost:8080'
HudsonCliApi cliApi = new HudsonCliApi()
OutputStream out = new ByteArrayOutputStream()
cliApi.runCliCommand(rootUrl, ['groovysh', 'hudson.jobNames.inspect()'], System.in, out, System.err)
List allJobs = Eval.me(cliApi.parseResponse(out.toString()))

