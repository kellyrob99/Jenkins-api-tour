@GrabResolver(name = 'glassfish', root = 'http://maven.glassfish.org/content/groups/public/')
@GrabResolver(name = "github", root = "http://kellyrob99.github.com/Jenkins-api-tour/repository")
@Grab('org.kar:hudson-api:0.2-SNAPSHOT')
@GrabExclude('org.codehaus.groovy:groovy')
import org.kar.hudson.api.cli.HudsonCliApi

String rootUrl = 'http://localhost:8080'
HudsonCliApi cliApi = new HudsonCliApi()
OutputStream out = new ByteArrayOutputStream()
def script = '''hudson.items.findAll{ job ->
    job.isBuildable() && job.lastBuild && job.lastBuild.result == Result.FAILURE
}.collect{it.name}.inspect()
'''
cliApi.runCliCommand(rootUrl, ['groovysh', script], System.in, out, System.err)
List failedJobs = Eval.me(cliApi.parseResponse(out.toString()))
failedJobs.each{ job ->
    cliApi.runCliCommand(rootUrl, ['build', job])
}

