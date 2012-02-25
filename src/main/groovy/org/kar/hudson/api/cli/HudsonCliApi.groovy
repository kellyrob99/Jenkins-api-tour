package org.kar.hudson.api.cli

import static groovyx.net.http.ContentType.BINARY
import static groovyx.net.http.Method.GET
import groovyx.net.http.HTTPBuilder
import hudson.cli.CLI

/**
 * @author Kelly Robinson
 */
class HudsonCliApi
{
    static final String JAR_DIR = '/.jenkinsApi/'
    static final String JAR_NAME = 'jenkins-cli.jar'
    static final String URI_PATH = "/jnlpJars/$JAR_NAME"
    static final String FINAL_PATH = JAR_DIR + JAR_NAME

    /**
     * Download the cli jar from the specified server.
     * @param rootUrl  of the server to download from
     */
    def downloadJar(String rootUrl)
    {
        def userHome = System.getProperty('user.home')
        println "downloading $rootUrl to $userHome/$FINAL_PATH"
        HTTPBuilder http = new HTTPBuilder(rootUrl)
        http.request(GET, BINARY) { req ->
            uri.path = URI_PATH

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

    /**
     * Drive the CLI with a single argument to execute.
     * Optionally accepts streams for input, output and err, all of which
     * are set by default to System unless otherwise specified.
     * @param rootUrl
     * @param arg
     * @param input
     * @param output
     * @param err
     * @return
     */
    def runCliCommand(String rootUrl, String arg, InputStream input = System.in, OutputStream output = System.out, OutputStream err = System.err)
    {
        runCliCommand(rootUrl, [arg], input, output, err)
    }

    /**
     * Drive the CLI with multiple arguments to execute.
     * Optionally accepts streams for input, output and err, all of which
     * are set by default to System unless otherwise specified.
     * @param rootUrl
     * @param args
     * @param input
     * @param output
     * @param err
     * @return
     */
    def runCliCommand(String rootUrl, List<String> args, InputStream input = System.in,
            OutputStream output = System.out, OutputStream err = System.err)
    {
        def CLI cli = new CLI(rootUrl.toURI().toURL())
        cli.execute(args, input, output, err)
        cli.close()
    }

    String parseResponse(String response)
    {
        return response.substring(11)
    }
}
