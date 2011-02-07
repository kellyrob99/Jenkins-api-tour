package org.kar.hudson.api

import groovy.xml.XmlUtil
import spock.lang.Specification
import static java.net.HttpURLConnection.*
/**
 * Depends on having Hudson running on the local machine.
 *
 * @author Kelly Robinson
 */
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

        then:
        hudsonInfo.jobs.size() > 3    //unfortunate hard-coded value
        hudsonInfo.each {println it}
        println 'main hudson api finished'.center(40, '*')

        when:
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
            //turned off since the output includes the entire build log!
//            jobDetails.builds.each { build ->
//                println  jobApi.inspectBuildTests(build.url)
//            }
            println ''.center(40, '*')

            final config = configApi.loadJobConfig(job.url)
            println XmlUtil.serialize(config)

            println ''.center(40, '*')

        }

        then:
        true

        when:
        final testJob = hudsonInfo.jobs.find {it.name.contains('hudson')}
        HTTP_MOVED_TEMP == jobApi.copyJob(rootUrl, [name: 'myNewJob', mode: 'copy', from: testJob.name])
        HTTP_MOVED_TEMP == jobApi.deleteJob(rootUrl + 'job/myNewJob/')
        then:
        true

        when:
        final config = new JobConfigXmlAPI().loadJobConfig(testJob.url)
        HTTP_OK == jobApi.createJob(rootUrl, XmlUtil.serialize(config), 'blah')
        HTTP_MOVED_TEMP == jobApi.deleteJob(rootUrl + 'job/blah/')

        then:
        true


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
