#!/bin/bash

# initialize input data
taxirides_filepath="$(readlink -f data/taxirides.2019.sorted.csv.gz)"

# initialize commands
common_consumer_cmd="./gradlew common-consumer:bootRun -Pargs=\"--kafka.topic="
taxiride_producer_cmd="./gradlew taxiride:bootRun -PtaxiRideStart --args=\"-c ${taxirides_filepath} -s 2019-01-01T00:00:00 -b 10000 -h 995\""

# run stop command killing all but the current pane
if [[ "$1" == "stop" ]]; then
  tmux kill-pane -a -t0
  ./gradlew composeDown
  exit 0
elif [[ "$1" == "start" ]]; then
  # initialize environment
  ./gradlew composeDown composeUp

  tmux splitw -h "${common_consumer_cmd}taxiridestarts\""
  tmux splitw -p 66 -v "${common_consumer_cmd}intermediateprices\""
  tmux splitw -v "${common_consumer_cmd}taxirideends\""

  tmux selectp -t0
  eval "${taxiride_producer_cmd}"
fi
