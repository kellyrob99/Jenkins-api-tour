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
    public static final String TEST_JOB_NAME = 'test'
    public static final String NEW_JOB_NAME = 'myNewJob'
    def rootUrl = 'http://localhost:8080/'
    def api = new JSONApi()
    def jobApi = new JobJSONApi()
    def configApi = new JobConfigXmlAPI()

    def "test creating, copying and deleting jobs"()
    {
        //create a new job based from a config.xml file
        when:
        final createJobResult = jobApi.createJob(rootUrl, XmlUtil.serialize(new File(this.getClass().classLoader
                .getResource('config.xml').toURI()).text), TEST_JOB_NAME)
        def hudsonInfo = api.inspectApi(rootUrl)
        final testJob = hudsonInfo.jobs.find {it.name == TEST_JOB_NAME}

        then:
        HTTP_OK == createJobResult
        hudsonInfo.jobs.size() > 0
        testJob.name == TEST_JOB_NAME

        //copy an existing job
        when:
        def copyJobResult = jobApi.copyJob(rootUrl, [name: NEW_JOB_NAME, mode: 'copy', from: testJob.name])
        hudsonInfo = api.inspectApi(rootUrl)
        final newTestJob = hudsonInfo.jobs.find {it.name == NEW_JOB_NAME}

        then:
        HTTP_MOVED_TEMP == copyJobResult
        newTestJob != null
        newTestJob.name == NEW_JOB_NAME

        //delete copied job
        when:
        def deleteJobResult = jobApi.deleteJob(newTestJob.url)
        hudsonInfo = api.inspectApi(rootUrl)
        def deletedJob = hudsonInfo.jobs.find {it.name == NEW_JOB_NAME}
        then:
        HTTP_MOVED_TEMP == deleteJobResult
        deletedJob == null
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
        computer.computer.displayName == 'master'
    }
}
