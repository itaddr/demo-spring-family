#!/bin/bash

# 判断是否存在java环境
[[ ! -x "$(command -v java)" ]] && echo "Please set the JAVA_HOME variable in your environment, We need java(x64)!" && exit 1

[[ ! ${LOGS_OUTPUT} ]] && LOGS_OUTPUT="file"
JAVA_OPTS="${JAVA_OPTS} -Dlogs.output=${LOGS_OUTPUT}"

if [[ "file" = ${LOGS_OUTPUT} ]];
then
    [[ ! ${LOGS_DIR} ]] && LOGS_DIR="/opt/logs"
    [[ ! ${LOGS_NAMESPACE} ]] && LOGS_NAMESPACE="service"
    [[ ! -d ${LOGS_DIR} ]] && mkdir -p /opt/logs/

    JAVA_OPTS="${JAVA_OPTS} -Dlogs.dir=${LOGS_DIR} -Dlogs.namespace=${LOGS_NAMESPACE}"
elif [[ "console" != ${LOGS_OUTPUT} ]];
then
    echo "Wrong LOGS_OUTPUT environment"
    exit 1
fi

java ${JAVA_OPTS} -jar $1 > ${LOGS_DIR}/stdout.out 2>&1
