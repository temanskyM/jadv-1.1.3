import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class RecursiveSumTask extends RecursiveTask<Integer> {
    private int[] arr;

    private static final int THRESHOLD = 20_000_000;

    public RecursiveSumTask(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected Integer compute() {
        if (arr.length > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubtasks())
                    .stream()
                    .mapToInt(ForkJoinTask::join)
                    .sum();
        } else {
            return processing(arr);
        }
    }

    private Integer processing(int[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
        // return Arrays.stream(arr).sum();
    }

    private List<RecursiveSumTask> createSubtasks() {
        List<RecursiveSumTask> subTasks = new ArrayList<>();
        subTasks.add(new RecursiveSumTask(
                Arrays.copyOfRange(arr, 0, arr.length / 2)));
        subTasks.add(new RecursiveSumTask(
                Arrays.copyOfRange(arr, arr.length / 2, arr.length)));
        return subTasks;
    }

}
