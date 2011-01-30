package org.kar.hudson.api

import spock.lang.Specification
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

/**
 * Depends on having Hudson running on the local machine.
 *
 * @author Kelly Robinson
 */
class JSONApiTest extends Specification
{
    def rootUrl = 'http://localhost:8080/'
    def api = new JSONApi()

    def "test loading the api"()
    {
        when:
        def hudsonInfo = api.inspectApi(rootUrl)

        then:
        hudsonInfo.jobs.size() == 3    //unfortunate hard-coded value
        hudsonInfo.each {println it}
        println 'main hudson api finished'.center(40, '*')

        when:
        def jobApi = new JobJSONApi()
        def configApi = new JobConfigXmlAPI()

        hudsonInfo.jobs.each { job ->
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
        final testJob = hudsonInfo.jobs.find {it.name.equals('test')}
        302 == jobApi.copyJob(rootUrl, [name:'myNewJob', mode:'copy', from: testJob.name])
        302 == jobApi.deleteJob(rootUrl+'job/myNewJob/')
        then:
        true

        when:
        final config = new JobConfigXmlAPI().loadJobConfig(testJob.url)
        200 == jobApi.createJob(rootUrl, XmlUtil.serialize(config) , 'blah')
        302 == jobApi.deleteJob(rootUrl+'job/blah/')

        then:
        true


    }

    def "test loading the computer info"()
    {
        when:
        def computer = api.inspectComputer(rootUrl)

        then:
        computer.each{
            println it
        }
        println "computer.computer.displayName is ${computer.computer.displayName}"
    }
}
