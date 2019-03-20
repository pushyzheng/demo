--
-- Author: Pushy
-- Since: 2019/3/5 9:41
--

local lock_key = KEYS[1]
local identifier = KEYS[2]

if redis.call('get', lock_key) == identifier
then
    -- 删除成功，即释放锁成功，返回 1
--    return tonumber(redis.call('del', lock_key))
    return 1
else
    -- 删除失败，即释放锁失败，返回 0
    return 0
end