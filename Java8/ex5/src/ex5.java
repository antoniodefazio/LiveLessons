import utils.ExceptionUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This example shows how a modern Java {@link Consumer} interface can
 * be used with the {@code forEach()} method to print out the values
 * in a list by binding the {@code System.out.println()} method to the
 * {@code forEach()} {@link Consumer} parameter.  It also shows how to
 * sort a {@link List} in ascending and descending order using a
 * {@link Comparator} and a {@link Function} functional interface.
 */
public class ex5 {
    static class MyThread extends Thread {
        MyThread(String name) {
            super(name);
        }
        @Override
        public void run() {
            System.out.println(this.getName());
        }
    }

    static public void main(String[] argv) {
        // Create a list of Thread objects.
        List<Thread> threads = Arrays
            .asList(new MyThread("Larry"),
                    new MyThread("Curly"),
                    new MyThread("Moe"));

        // forEach() takes a Consumer, which is bound to the
        // System.out println() method.
        threads.forEach(System.out::println);

        // Sort the threads by their names in ascending order.
        threads.sort(Comparator.comparing(Thread::getName));

        // forEach() takes a Consumer, which is bound to the
        // System.out println() method.
        threads.forEach(System.out::println);

        // Sort the threads by their names in descending order.
        threads.sort(Comparator.comparing(Thread::getName).reversed());

        // forEach() takes a Consumer, which is bound to the
        // System.out println() method.
        threads.forEach(System.out::println);

        // Iterate through the list of threads and pass a method
        // reference that starts each thread.
        threads.forEach(Thread::start);

        // This concise solution iterates through the threads and pass
        // the Thread.join() method reference as a barrier
        // synchronizer to wait for all threads to finish.  Note how
        // rethrowConsumer() converts a checked exception to an
        // unchecked exception.
        threads
            .forEach(ExceptionUtils.rethrowConsumer(Thread::join));
    }
}

