package com.sici.framework.redis.batch.zset;

import org.springframework.data.redis.connection.zset.Tuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.lang.Nullable;

import java.util.Set;

/**
 * @author jackey
 * @description ZSet操作
 * @date 5/26/25 11:53
 */
public interface ZSetOperation<IN, V> {
    Boolean add(IN req, V value);

    Boolean add(IN req, V value, double score);

    /**
     * Add {@code value} to a sorted set at {@code key} if it does not already exists.
     *
     * @param value the value.
     * @param score the score.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zadd">Redis Documentation: ZADD NX</a>
     * @since 2.5
     */
    Boolean addIfAbsent(IN req, V value, double score);

    Long remove(IN req, V... values);

    Long size(IN req);

    Double score(IN req, V o);

    /**
     * Get elements between {@code start} and {@code end} from sorted set.
     *
     * @param start
     * @param end
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrange">Redis Documentation: ZRANGE</a>
     */
    @Nullable
    Set<V> range(IN req, long start, long end);

    /**
     * Get set of {@link Tuple}s between {@code start} and {@code end} from sorted set.
     *
     * @param start
     * @param end
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrange">Redis Documentation: ZRANGE</a>
     */
    @Nullable
    Set<ZSetOperations.TypedTuple<V>> rangeWithScores(IN req, long start, long end);

    /**
     * Get elements where score is between {@code min} and {@code max} from sorted set.
     *
     * @param min
     * @param max
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrangebyscore">Redis Documentation: ZRANGEBYSCORE</a>
     */
    @Nullable
    Set<V> rangeByScore(IN req, double min, double max);

    /**
     * Get set of {@link Tuple}s where score is between {@code min} and {@code max} from sorted set.
     *
     * @param min
     * @param max
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrangebyscore">Redis Documentation: ZRANGEBYSCORE</a>
     */
    @Nullable
    Set<ZSetOperations.TypedTuple<V>> rangeByScoreWithScores(IN req, double min, double max);

    /**
     * Get elements in range from {@code start} to {@code end} where score is between {@code min} and {@code max} from
     * sorted set.
     *
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrangebyscore">Redis Documentation: ZRANGEBYSCORE</a>
     */
    @Nullable
    Set<V> rangeByScore(IN req, double min, double max, long offset, long count);

    /**
     * Get set of {@link Tuple}s in range from {@code start} to {@code end} where score is between {@code min} and
     * {@code max} from sorted set.
     *
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrangebyscore">Redis Documentation: ZRANGEBYSCORE</a>
     */
    @Nullable
    Set<ZSetOperations.TypedTuple<V>> rangeByScoreWithScores(IN req, double min, double max, long offset, long count);

    /**
     * Get elements in range from {@code start} to {@code end} from sorted set ordered from high to low.
     *
     * @param start
     * @param end
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrange">Redis Documentation: ZREVRANGE</a>
     */
    @Nullable
    Set<V> reverseRange(IN req, long start, long end);

    /**
     * Get set of {@link Tuple}s in range from {@code start} to {@code end} from sorted set ordered from high to low.
     *
     * @param start
     * @param end
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrange">Redis Documentation: ZREVRANGE</a>
     */
    @Nullable
    Set<ZSetOperations.TypedTuple<V>> reverseRangeWithScores(IN req, long start, long end);

    /**
     * Get elements where score is between {@code min} and {@code max} from sorted set ordered from high to low.
     *
     * @param min
     * @param max
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrangebyscore">Redis Documentation: ZREVRANGEBYSCORE</a>
     */
    @Nullable
    Set<V> reverseRangeByScore(IN req, double min, double max);

    /**
     * Get set of {@link Tuple} where score is between {@code min} and {@code max} from sorted set ordered from high to
     * low.
     *
     * @param min
     * @param max
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrangebyscore">Redis Documentation: ZREVRANGEBYSCORE</a>
     */
    @Nullable
    Set<ZSetOperations.TypedTuple<V>> reverseRangeByScoreWithScores(IN req, double min, double max);

    /**
     * Get elements in range from {@code start} to {@code end} where score is between {@code min} and {@code max} from
     * sorted set ordered high -> low.
     *
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrangebyscore">Redis Documentation: ZREVRANGEBYSCORE</a>
     */
    @Nullable
    Set<V> reverseRangeByScore(IN req, double min, double max, long offset, long count);

    /**
     * Get set of {@link Tuple} in range from {@code start} to {@code end} where score is between {@code min} and
     * {@code max} from sorted set ordered high -> low.
     *
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrangebyscore">Redis Documentation: ZREVRANGEBYSCORE</a>
     */
    @Nullable
    Set<ZSetOperations.TypedTuple<V>> reverseRangeByScoreWithScores(IN req, double min, double max, long offset, long count);

    /**
     * Count number of elements within sorted set with scores between {@code min} and {@code max}.
     *
     * @param min
     * @param max
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zcount">Redis Documentation: ZCOUNT</a>
     */
    @Nullable
    Long count(IN req, double min, double max);
}
