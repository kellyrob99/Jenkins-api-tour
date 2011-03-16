package org.kar.hudson.api

import groovy.xml.XmlUtil
import spock.lang.Specification
import static java.net.HttpURLConnection.*

/**
 * Depends on having Hudson running on the local machine.
 *
 * @author Kelly Robinson
 */
@SuppressWarnings('MethodName')
class JSONApiTest extends Specification
{
    def rootUrl = 'http://localhost:8080/'
    def api = new JSONApi()
    def jobApi = new JobJSONApi()
    def configApi = new JobConfigXmlAPI()

    def "test loading the api"()
    {
        when:
        def hudsonInfo = api.inspectApi(rootUrl)
        hudsonInfo.jobs.each { job ->
            jobApi.triggerBuild(job.url)
            jobApi.inspectSuccessfulJob(job.url).each { jobInfo ->
                jobInfo.each {
                    println it
                }
            }
            println ''.center(40, '*')
            final jobDetails = jobApi.inspectJob(job.url)
            jobDetails.each { jobInfo ->
                jobInfo.each {
                    println it
                }
            }
            jobDetails.builds.each { build ->
                println jobApi.inspectBuildTests(build.url)
            }
            println ''.center(40, '*')

            final config = configApi.loadJobConfig(job.url)
            println XmlUtil.serialize(config)

            println ''.center(40, '*')

        }

        then:
        hudsonInfo.jobs.size() > 0
        println hudsonInfo.toString()
        hudsonInfo.each {println it}
        println 'main hudson api finished'.center(40, '*')

        final testJob = hudsonInfo.jobs.find {it.name.contains('test')}

        //copy an existing job
        when:
        def copyJobResult = jobApi.copyJob(rootUrl, [name: 'myNewJob', mode: 'copy', from: testJob.name])
        def deleteJobResult = jobApi.deleteJob(rootUrl + 'job/myNewJob/')

        then:
        HTTP_MOVED_TEMP == copyJobResult
        HTTP_MOVED_TEMP == deleteJobResult

        //create a new job based on a downloaded config.xml file
        when:
        final config = new JobConfigXmlAPI().loadJobConfig(testJob.url)
        final createJobResult = jobApi.createJob(rootUrl, XmlUtil.serialize(config), 'blah')
        final deleteCreatedJobResult = jobApi.deleteJob(rootUrl + 'job/blah/')

        then:
        HTTP_OK == createJobResult
        HTTP_MOVED_TEMP == deleteCreatedJobResult
    }

    def "test a POST failure"()
    {
        when:
        def status = jobApi.deleteJob("$rootUrl/job/randomJob")

        then:
        status == HTTP_NOT_FOUND
    }

    def "test loading the computer info"()
    {
        when:
        def computer = api.inspectComputer(rootUrl)

        then:
        computer.each {
            println it
        }
        println "computer.computer.displayName is ${computer.computer.displayName}"
    }
}
