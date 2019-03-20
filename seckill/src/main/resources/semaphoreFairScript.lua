--
-- 公平分布式信号量获取 Lua 脚本
--
-- Author: Pushy
-- Since: 2019/3/4 12:44
--
local identifier = KEYS[1]
local key = KEYS[2]
local czset = key .. ":owner"
local ctr = key .. ":counter"

local now = tonumber(ARGV[1])
local timeout = tonumber(ARGV[2])
local limit = tonumber(ARGV[3])

-- 将过期的信号量删除
redis.call('zremrangebyscore', key, 0, now - timeout)
redis.call('zinterstore', czset, 2, czset, key)

local counter = tonumber(redis.call('incr', ctr))
-- 将新的信号量加入
redis.call('zadd', key, now, identifier)
redis.call('zadd', czset, counter, identifier)

-- 获取刚插入的信号量在有序集和中的排名
-- 如果排名低于可获取的信号量总数，则表示信号量获取成功
local rank = tonumber(redis.call('zrank', key, identifier))
if rank < limit then
    return 1
else
    -- 获取信号量失败，删除新添加的信号量
    redis.call('zrem', key, identifier)
    return 0
end