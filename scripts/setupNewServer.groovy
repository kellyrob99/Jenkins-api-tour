@GrabResolver(name='glassfish', root='http://maven.glassfish.org/content/groups/public/')
@GrabResolver(name="github", root="http://kellyrob99.github.com/Jenkins-api-tour/repository")
@Grab('org.kar:hudson-api:0.2-SNAPSHOT')
@GrabExclude('org.codehaus.groovy:groovy')
import static java.net.HttpURLConnection.*
import org.kar.hudson.api.*
import org.kar.hudson.api.cli.HudsonCliApi

String rootUrl = 'http://localhost:8080'
HudsonCliApi cliApi = new HudsonCliApi()

['groovy', 'gradle', 'chucknorris', 'greenballs', 'github', 'analysis-core', 'analysis-collector', 'cobertura',
        'project-stats-plugin','audit-trail', 'view-job-filters', 'disk-usage', 'global-build-stats',
        'radiatorviewplugin', 'violations', 'build-pipeline-plugin', 'monitoring', 'dashboard-view',
        'iphoneview', 'jenkinswalldisplay'].each{ plugin ->
    cliApi.runCliCommand(rootUrl, ['install-plugin', plugin])
}

//  Restart a node, required for newly installed plugins to be made available.
cliApi.runCliCommand(rootUrl, 'safe-restart')
