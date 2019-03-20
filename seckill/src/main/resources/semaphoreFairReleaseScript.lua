--
-- Author: Pushy
-- Since: 2019/3/4 14:33
--

local identifier = KEYS[1]
local key = KEYS[2]

redis.call('zrem', key, identifier)
local result = tonumber(redis.call('zrem', key .. ":owner", identifier))
return result