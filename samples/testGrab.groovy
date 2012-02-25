@GrabResolver(name='glassfish', root='http://maven.glassfish.org/content/groups/public/')
@GrabResolver(name="github", root="http://kellyrob99.github.com/Jenkins-api-tour/repository")
@Grab('org.kar:hudson-api:0.2-SNAPSHOT')
@GrabExclude('org.codehaus.groovy:groovy')
import static java.net.HttpURLConnection.*
import org.kar.hudson.api.*
import org.kar.hudson.api.cli.HudsonCliApi

String rootUrl = 'http://localhost:8080'
JSONApi api = new JSONApi()
JobJSONApi jobApi = new JobJSONApi()
HudsonCliApi cliApi = new HudsonCliApi()
HudsonControlApi controlApi = new HudsonControlApi()

/* Example usages */
//  Create a new job based on a config.xml file. Useful for copying a build from one node to another.
assert HTTP_OK == jobApi.createJob(rootUrl, new File('../src/test/resources/config.xml').text, 'test2')

// And then delete that job
apiResult = api.inspectApi(rootUrl)
def newJob = apiResult.jobs.find{it.name == 'test2'}
assert HTTP_MOVED_TEMP == jobApi.deleteJob(newJob.url)

//  Execute scripts remotely. Output streams are printed to System.err and System.out,
// so println's on the server appear locally.
def listPlugins = 'Hudson.instance.pluginManager.plugins.each { \
println("${it.longName} - ${it.version}") };'

def allFailedBuilds = '''hudsonInstance = Hudson.instance
allItems = hudsonInstance.items
activeJobs = allItems.findAll{job -> job.isBuildable()}
failedRuns = activeJobs.findAll{job -> job.lastBuild && job.lastBuild.result == Result.FAILURE}
failedRuns.each{run -> println(run.name)}'''

def parseableAllFailedBuilds = '''hudsonInstance = Hudson.instance
allItems = hudsonInstance.items
activeJobs = allItems.findAll{job -> job.isBuildable()}
failedRuns = activeJobs.findAll{job -> job.lastBuild && job.lastBuild.result == Result.FAILURE}
[activeJobs:activeJobs?.collect{it.name}, failedRuns:failedRuns?.collect{it.name}].inspect()'''

[listPlugins, allFailedBuilds, parseableAllFailedBuilds].each{ script ->
    cliApi.runCliCommand(rootUrl, ['groovysh', script])
}

// Execute a script remotely and capture the output for further study.
OutputStream output = new ByteArrayOutputStream()
cliApi.runCliCommand(rootUrl, ['groovysh', parseableAllFailedBuilds],System.in, output, System.err)
def mapOfBuildsString = cliApi.parseResponse(output.toString())
Map mapOfBuilds = Eval.me(mapOfBuildsString)  // convert to a map using the easiest available method
assert mapOfBuilds.activeJobs
assert mapOfBuilds.failedRuns

//  Install a new plugin. Easily automates configuring a new node with the required plugins.
//  This will also upgrade a plugin to the latest version if it is already installed.
cliApi.runCliCommand(rootUrl, ['install-plugin', 'jira'])

//  Inform a node that it should get ready to shut down.
println controlApi.sendQuiet(rootUrl)

//  Restart a node, required for newly installed plugins to be made available.
cliApi.runCliCommand(rootUrl, 'safe-restart')

