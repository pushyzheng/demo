--
-- Author: Pushy
-- Since: 2019/3/4 14:33
--

local identifier = KEYS[1]
local key = KEYS[2]

local result = tonumber(redis.call('zrem', key, identifier))
return result