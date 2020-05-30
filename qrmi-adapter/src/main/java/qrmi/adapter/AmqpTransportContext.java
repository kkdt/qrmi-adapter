package qrmi.adapter;

/**
 * Contextual object that holds the {@link ThreadLocal} containing the {@link AmqpTransport}.
 *
 */
public final class AmqpTransportContext {
    private static ThreadLocal<AmqpTransport> threadLocal = new ThreadLocal<>();

    public static void set(AmqpTransport rabbitAccess) {
        threadLocal.set(rabbitAccess);
    }

    public static AmqpTransport get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }

}
