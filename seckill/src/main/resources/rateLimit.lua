local key = "rate.limit:" .. KEYS[1] -- 限流KEY
local limit = tonumber(ARGV[1])      -- 限流大小
-- 获取当前的流量大小
local current = tonumber(redis.call('get', key) or "0")

if current + 1 > limit then
    -- 达到限流的大小，返回
    return 0
else
    -- 没有到达阈值，value + 1
    redis.call("INCRBY", key, "1")
    redis.call("expire", key, "2")
    return current + 1
end