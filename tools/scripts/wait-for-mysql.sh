#!/bin/sh

set -e

host="$1"
port="$2"
shift 2
cmd="$@"

if [ -z "$port" ]; then
  echo "Port not specified, using default MySQL port 3306"
  port=3306
fi

until echo > /dev/tcp/$host/$port; do
  echo "Waiting for MySQL to be available at $host:$port..."
  sleep 1
done

echo "MySQL is up - executing command"
exec $cmd
