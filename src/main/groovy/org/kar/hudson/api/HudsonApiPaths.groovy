package org.kar.hudson.api

/**
 * @author Kelly Robinson
 */
public interface HudsonApiPaths
{
    static final String QUIET = 'quietDown'
    static final String CANCEL_QUIET = 'cancelQuietDown'
    static final String CREATE_ITEM = 'createItem'
    static final String RESTART = 'restart'
    static final String SAFE_RESTART  = 'safeRestart'

    static final String API_JSON = 'api/json'
    static final String COMPUTER = 'computer/' + API_JSON

    static final String LAST_SUCCESSFUL = 'lastSuccessfulBuild/' + API_JSON
    static final String TEST_REPORT = 'testReport/' + API_JSON
    static final String BUILD = 'build'
    static final String DELETE_JOB = 'doDelete'
}