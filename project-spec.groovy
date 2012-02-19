/**
 * @author Kelly Robinson
 */

spec = [
        name: 'hudson-api',
        group: 'org.kar',
        version: '0.1',

        versions: [
                groovy: '1.8.6',
                jenkins: '1.451'
        ],
]

/**
 * External dependencies
 */
spec.libraries = [
        groovy: "org.codehaus.groovy:groovy-all:${spec.versions.groovy}",
        spock : [
                'org.spockframework:spock-core:0.5-groovy-1.8',
                'org.hamcrest:hamcrest-core:1.1',
                'cglib:cglib-nodep:2.2',
                'org.objenesis:objenesis:1.2'],
        ivy: "org.apache.ivy:ivy:2.0.0",
        httpBuilder: 'org.codehaus.groovy.modules.http-builder:http-builder:0.5.1',
        junit: 'junit:junit:4.8.2',
        jenkinsCli: ["org.jenkins-ci.main:cli:${spec.versions.jenkins}","org.jenkins-ci.main:remoting:2.12"],
        jenkins: "org.jenkins-ci.main:jenkins-war:${spec.versions.jenkins}@war"
]
