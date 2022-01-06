#!/bin/fish

# Figure out the next days number
set dirs (ls -F | grep '^d.*$')
set nums
for d in $dirs
    set nums $nums (echo $d | cut --characters=2-3)
end
set num (math $nums[-1] + 1)
set padded_num (printf "%02d" $num)

# Create new dir and prep input for the next day
set new_dir d$padded_num
mkdir -p $new_dir
curl --cookie session=(cat session) \
  https://adventofcode.com/2021/day/$num/input \
  > $new_dir/input

cd d$padded_num
