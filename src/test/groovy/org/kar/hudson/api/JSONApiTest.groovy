package org.kar.hudson.api

import spock.lang.Specification

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
        println ''.center(40, '*')

        when:
        def jobApi = new JobJSONApi()
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
        }

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
    }
}
