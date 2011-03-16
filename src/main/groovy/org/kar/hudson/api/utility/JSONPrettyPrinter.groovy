package org.kar.hudson.api.utility

/**
 * More of a local convenience than anything, utilizes an installed Ruby utility program to
 * pretty-print JSON.
 *
 * @author Kelly Robinson
 */
class JSONPrettyPrinter
{

    String prettyPrint(String content)
    {
        final process = ['echo', content].execute().pipeTo('prettify_json.rb'.execute())
        process.waitFor()
        process.text
    }
}
