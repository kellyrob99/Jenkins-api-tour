package org.kar.hudson.api.cli

import groovyx.net.http.HTTPBuilder
import hudson.cli.CLI
import static groovyx.net.http.ContentType.BINARY
import static groovyx.net.http.Method.GET

/**
 * @author Kelly Robinson
 */
class HudsonCliApi
{
//    http://localhost:8080/jnlpJars/hudson-cli.jar
    static final String JAR_DIR = '/.hudsonApi/'
    static final String JAR_NAME = 'hudson-cli.jar'
    static final String FINAL_PATH = JAR_DIR + JAR_NAME

    def downloadJar(String rootUrl)
    {
        def userHome = System.getProperty('user.home')
        println "downloading $rootUrl to $userHome/$FINAL_PATH"
        HTTPBuilder http = new HTTPBuilder(rootUrl)
        http.request(GET, BINARY) { req ->
            uri.path = '/jnlpJars/hudson-cli.jar'

            response.success = { resp, reader ->
                println "Got response: ${resp.statusLine}"
                println "Content-Type: ${resp.headers.'Content-Type'}"
                final File hudsonJarDir = new File("$userHome/$JAR_DIR")
                hudsonJarDir.mkdirs()
                final File hudsonJarFile = new File(hudsonJarDir, JAR_NAME)
                if (hudsonJarFile.exists())
                {
                    hudsonJarFile.delete()
                }
                hudsonJarFile.createNewFile()
                hudsonJarFile << reader
            }
        }
    }

    def runCliCommand(String rootUrl, String arg, InputStream input = System.in, OutputStream output = System.out, OutputStream err = System.err)
    {
        runCliCommand(rootUrl, [arg], input, output, err)
    }

    def runCliCommand(String rootUrl, List<String> args, InputStream input = System.in, OutputStream output = System.out, OutputStream err = System.err)
    {
        def CLI cli = new CLI(rootUrl.toURI().toURL())
        cli.execute(args, input, output, err)
    }


}
