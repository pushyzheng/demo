--
-- Author: Pushy
-- Since: 2019/3/5 9:47
--

local lock_key = KEYS[1]
local identifier = KEYS[2]
local timeout = tonumber(ARGV[1])

if redis.call('setnx', lock_key, identifier) == 1 then
    redis.call('expire', lock_key, timeout)
    return 1
else
    return 0
end