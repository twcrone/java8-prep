import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreams {

  public static void main(String[] args) {
//    System.out.println("Sequential sum done in: " + measureSumPerf(ParallelStreams::sequentialSum, 10_000_000) + " msecs");
//    System.out.println("Parallel sum done in: " + measureSumPerf(ParallelStreams::parallelSum, 10_000_000) + " msecs");
    System.out.println("Ranged sum done in: " + measureSumPerf(ParallelStreams::rangedSum, 10_000_000) + " msecs");
    System.out.println("Parallel ranged sum done in: " + measureSumPerf(ParallelStreams::parallelRangedSum, 10_000_000) + " msecs");
    System.out.println("Fork Join ranged sum done int " + measureSumPerf(ForkJoinSumCalculator::forkJoinSum, 10_000_000) + " msecs");
  }

  public static long measureSumPerf(Function<Long, Long> adder, long n) {
    long fastest = Long.MAX_VALUE;
    for (int i = 0; i < 10; i++) {
      long start = System.nanoTime();
      long sum = adder.apply(n);
      long duration = (System.nanoTime() - start) / 1_000_000;
      System.out.println("Result: " + sum);
      if (duration < fastest) fastest = duration;
    }
    return fastest;
  }

  public static long parallelRangedSum(long n) {
    return LongStream.rangeClosed(1, n)
        .parallel()
        .reduce(0L, Long::sum);
  }

  public static long parallelSum(long n) {
    return Stream.iterate(1L, i -> i + 1)
        .limit(n)
        .parallel()
        .reduce(0L, Long::sum);
  }

  public static long rangedSum(long n) {
    return LongStream.rangeClosed(1, n)
        .reduce(0L, Long::sum);
  }

  public static long sequentialSum(long n) {
    return Stream.iterate(1L, i -> i + 1)
        .limit(n)
        .reduce(0L, Long::sum);
  }
}
