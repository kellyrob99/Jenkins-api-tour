# A Tour of the Jenkins(Hudson too) API
=====================================

By whichever name you like, this build server provides a rich set of APIs you can trigger remotely in a variety of ways.
This project explores the APIs and provides some simple examples of how to get things done.

## Highlights of the JSON/XML APIs
 - extracting information from builds
 - triggering builds
 - remote shutdown of server

#### Examples


## Highlights of the CLI jar
 - installing plugins
 - executing Groovy scripts on the server

#### Examples

    @GrabResolver(name = 'glassfish', root = 'http://maven.glassfish.org/content/groups/public/')
    @GrabResolver(name = "github", root = "http://kellyrob99.github.com/Jenkins-api-tour/repository")
    @Grab('org.kar:hudson-api:0.2-SNAPSHOT')
    @GrabExclude('org.codehaus.groovy:groovy')
    import org.kar.hudson.api.cli.HudsonCliApi

    String rootUrl = 'http://localhost:8080'
    HudsonCliApi cliApi = new HudsonCliApi()
    OutputStream out = new ByteArrayOutputStream()
    cliApi.runCliCommand(rootUrl, ['groovysh', 'hudson.jobNames.inspect()'], System.in, out, System.err)
    List allJobs = Eval.me(cliApi.parseResponse(out.toString()))
    println allJobs



