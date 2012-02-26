# A Tour of the Jenkins(Hudson too) API
=====================================

By whichever name you like, this build server provides a rich set of APIs you can trigger remotely in a variety of ways.
This project explores the APIs and provides some simple examples of how to get things done.

## Highlights of the JSON/XML APIs
 - extracting information from builds
 - triggering builds
 - remote shutdown of server

#### Examples

**Get the names of all Jobs from the server**
    @GrabResolver(name = 'glassfish', root = 'http://maven.glassfish.org/content/groups/public/')
    @GrabResolver(name = "github", root = "http://kellyrob99.github.com/Jenkins-api-tour/repository")
    @Grab('org.kar:hudson-api:0.2-SNAPSHOT')
    @GrabExclude('org.codehaus.groovy:groovy')
    import org.kar.hudson.api.JSONApi

    String rootUrl = 'http://localhost:8080'
    JSONApi api = new JSONApi()
    def hudsonInfo = api.inspectApi(rootUrl)
    hudsonInfo.jobs.each{ job ->
        println job.name
    }

## Highlights of the CLI jar
 - installing plugins
 - executing Groovy scripts on the server

#### Examples

**Get the names of all Jobs from the server**

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

**List all installed Plugins and failed Builds**

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

## Getting the library

The jar built from this project is published on this site as a maven artifact. Here's how you can use it in your choice
of build system.

### Gradle

Add the following to build.gradle:

    repositories{
        mavenCentral()
        maven {
            url 'http://maven.glassfish.org/content/groups/public/'
        }
        maven {
            url 'http://kellyrob99.github.com/Jenkins-api-tour/repository'
        }
    }
    dependencies {
        groovy "org.codehaus.groovy:groovy-all:${versions.groovy}"
        compile 'org.kar:hudson-api:0.2-SNAPSHOT'
    }

### Maven

Add the following to pom.xml:

    <repositories>
        <repository>
            <id>glassfish</id>
            <name>glassfish</name>
            <url>http://maven.glassfish.org/content/groups/public/</url>
        </repository>
        <repository>
            <id>github</id>
            <name>Jenkins-api-tour maven repo on github</name>
            <url>http://kellyrob99.github.com/Jenkins-api-tour/repository</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>org.codehaus.groovy</groupId>
            <artifactId>groovy-all</artifactId>
            <version>${groovy.version}</version>
        </dependency>
        <dependency>
            <groupId>org.kar</groupId>
            <artifactId>hudson-api</artifactId>
            <version>0.2-SNAPSHOT</version>
        </dependency>
    </dependencies>

### Groovy Grapes

Use these annotations in a Groovy script:

    @GrabResolver(name='glassfish', root='http://maven.glassfish.org/content/groups/public/')
    @GrabResolver(name="github", root="http://kellyrob99.github.com/Jenkins-api-tour/repository")
    @Grab('org.kar:hudson-api:0.2-SNAPSHOT')
    @GrabExclude('org.codehaus.groovy:groovy')




