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

