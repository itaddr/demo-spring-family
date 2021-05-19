#!/bin/bash

# 获取程序包目录
_base_dir=$(cd `dirname $0`; cd ../; pwd)
_temp_dir="${_base_dir}/temp"
_pid_file="${_temp_dir}/pid"

# 判断进程缓存文件是否存在
[[ ! -s "${_pid_file}" ]] && echo "No file of the recording process ID is found." && exit 1

# 开始结束进程
_pid=`cat "${_pid_file}"`
if [[ -n "${_pid}" ]];
then
    echo "Begin shutdown ..."
    echo "Services are being released. Please wait patiently !!!"
    kill "${_pid}"
    for i in {1..599}
    do
        sleep 1 && _n_pid=`ps -ef | grep -w "${_pid}" | grep -v "grep" | awk '{print $2}'`
        [[ "${_pid}" != "${_n_pid}" ]] && echo "Shutdown success !!!" && rm -rf "${_pid_file}" && exit 0
    done
    echo "Unknown shutdown ..." && exit 1
else
    echo "No file of the recording process ID is found." && exit 1
fi
