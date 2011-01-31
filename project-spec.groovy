/**
 * @author Kelly Robinson
 */

spec = [
        name: 'hudson-api',
        group: 'org.kar',
        version: '0.1',

        versions: [
                groovy: '1.7.6',
        ],
]

/**
 * External dependencies
 */
spec.libraries = [
        groovy: "org.codehaus.groovy:groovy:${spec.versions.groovy}",
        spock : ['org.spockframework:spock-core:0.5-groovy-1.7@jar',
                'org.hamcrest:hamcrest-core:1.1@jar',
                'cglib:cglib-nodep:2.2',
                'org.objenesis:objenesis:1.2'],
        ivy: "org.apache.ivy:ivy:2.0.0",
        httpBuilder: 'org.codehaus.groovy.modules.http-builder:http-builder:0.5.1',
        junit: 'junit:junit:4.8.2'
]