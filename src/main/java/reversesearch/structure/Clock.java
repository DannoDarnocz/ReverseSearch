package reversesearch.structure;

import java.util.concurrent.TimeUnit;

public class Clock {
    private long start;
    private long end;

    public void start(){start=System.nanoTime();};
    public void end(){end=System.nanoTime();};
    public long getMilliseconds(){
        long nanoseconds = end-start;
        // java ya tiene para convertir de nanosegundos a mili
        return TimeUnit.NANOSECONDS.toMillis(nanoseconds);
    }
}
