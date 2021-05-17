#!/bin/bash

# 初始全局变量
_base_dir=$(cd `dirname $0`; cd ../; pwd)
_temp_dir="${_base_dir}/temp"
_pid_file="${_temp_dir}/pid"

# 判断进程缓存文件是否存在
[[ ! -s "${_pid_file}" ]] && exit 0

# 获取进程相关信息
_pid=`cat "${_pid_file}"`
[[ -n "${_pid}" ]] && echo `ps axu | grep -w "${_pid}" | grep -v "grep"` && exit 0
