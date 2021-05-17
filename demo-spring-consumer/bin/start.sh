#!/bin/bash

# 判断是否存在java环境
[[ ! -x "$(command -v java)" ]] && echo "Please set the JAVA_HOME variable in your environment, We need java(x64)!" && exit 1

# 初始全局变量
_base_dir=$(cd `dirname $0`; cd ../; pwd)

_conf_dir="${_base_dir}/conf"
_logs_dir="${_base_dir}/logs"
_temp_dir="${_base_dir}/temp"
_gcop_dir="${_logs_dir}/gcop"

_out_file="${_logs_dir}/stdout.out"
_pid_file="${_temp_dir}/pid"

_main_class_file="${_conf_dir}/MainClass"
_java_opts_file="${_conf_dir}/JavaOpts"

# 校验是否提供启动类
[[ ! -s "${_main_class_file}" ]] && echo "Please provide a startup class." && exit 1
_main_class=`cat ${_main_class_file}`
[[ ! -n "${_main_class}" ]] && echo "Please provide a startup class." && exit 1

# 读取虚拟机启动参数
[[ -s "${_java_opts_file}" ]] && _java_opts=`cat ${_java_opts_file}`

# 判断进程是否存在
if [[ -s "${_pid_file}" ]]
then
    _pid=`cat "${_pid_file}"`
    if [[ -n "${_pid}" ]]
    then
        _n_pid=`ps -ef | grep -w "${_pid}" | grep -v "grep" | awk '{print $2}'`
        [[ "${_pid}" = "${_n_pid}" ]] && echo "Process can not be restarted." && exit 1
    fi
fi

if [[ -d "${_temp_dir}" ]];
then
    rm -rf "${_temp_dir}/*"
else
    mkdir "${_temp_dir}"
fi
[[ ! -d "${_gcop_dir}" ]] && mkdir -p "${_gcop_dir}"
[[ -a "${_out_file}" ]] && echo "" > "${_out_file}"

#_java_opts="${_java_opts} -XX:+UseG1GC -XX:MaxGCPauseMillis=20 -XX:InitiatingHeapOccupancyPercent=35 -XX:+DisableExplicitGC"
_java_opts="${_java_opts} -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintTenuringDistribution -XX:+PrintGCApplicationStoppedTime -XX:+PrintAdaptiveSizePolicy"
_java_opts="${_java_opts} -Xloggc:${_gcop_dir}/gc_%p.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=128m"
_java_opts="${_java_opts} -Dbase.dir=${_base_dir} -Dconfig.dir=${_conf_dir} -Dlogs.output=file -Dlogs.dir=${_logs_dir} -Djava.io.tmpdir=${_temp_dir}"
_java_opts="${_java_opts} -Dspring.config.location=${_conf_dir}/ -cp ${_base_dir}/lib/*: ${_main_class} ${@}"

echo "java ${_java_opts}"
echo "Begin startup ..."
echo "The service is initializing, please wait patiently !!!"
nohup java ${_java_opts} >${_out_file} 2>&1 </dev/null &
_pid=$!

print_out()
{
    if [[ -s "$1" ]];
    then
        while read line
        do
            echo "$line"
        done < "$1"
    fi
}

if [[ $? -eq 0 ]];
then
    for i in {1..599}
    do
        sleep 1 && _n_pid=`ps -ef | grep -w "${_pid}" | grep -v "grep" | awk '{print $2}'`
        [[ -s "${_out_file}" && "${_pid}" = "${_n_pid}" ]] && echo "${_pid}" > "${_pid_file}" && echo "Startup success, process id [${_pid}]" && exit 0
    done
    echo "Unknown startup ..." && print_out "${_out_file}" && exit 1
else
    echo "Startup error ..." && print_out "${_out_file}" && exit 1
fi
