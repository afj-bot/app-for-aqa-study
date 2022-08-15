#!/bin/bash
STATUS=$1
PROJECT_NAME=$2
echo "Status of build is $STATUS"

generate_url () {
    regex="API"
    if [[ $PROJECT_NAME =~ $regex ]];
    then
        result="buy-it-api";
    else
        result="buy-it-ui";
    fi
    local url="https://app.circleci.com/pipelines/github/afj-bot/${result}?branch=master"
    echo "$url"
}

send_success_slack () {
    local title="The ${PROJECT_NAME} build was successfully finished."
    local emoji="partying_face"
    local color="#00FF00"
    local message='{"attachments": [{
        "title": "'${title}' :'${emoji}':",
        "text": "'${title}'",
        "color": "'${color}'"
    }]}'
    echo "Send succss slack notification. $message"
    resp="$(curl -d "$message" -H "Content-Type: application/json" -X POST ${SLACK_HOOK})"
    if [[ "${resp}" != 'ok' ]]; then
        echo -e "Failed to send slack notification.\n${resp}\n${message}"
    fi
}

send_failed_slack () {
    url="$(generate_url)"
    echo "Send failed slack notification with report: $url";
    local title="The ${PROJECT_NAME} build was finished with failure."
    local emoji="disappointed"
    local color="#FF0000"
    local text="${title}. Find it here $url"
    local message='{"attachments": [{
        "title": "'${title}' :'${emoji}':",
        "text": "'${text}'",
        "color": "'${color}'",
        "fields": [{
            "title": "Priority",
            "value": "High",
            "short": false
            }
        ],
    }]}'
    echo "Send succss slack notification. $message"
    resp="$(curl -d "$message" -H "Content-Type: application/json" -X POST ${SLACK_HOOK})"
    if [[ "${resp}" != 'ok' ]]; then
        echo -e "Failed to send slack notification.\n${resp}\n${message}"
    fi
}

if [[ "${STATUS}" == 'success' ]]; then
    send_success_slack
else
    send_failed_slack
fi
