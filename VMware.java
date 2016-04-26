import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        Long[][] connections = { {10000L,20000L,1411325674537L, 1411325726830L},
                {10002L, 20000L, 1411325508697L, 1411325726810L},
                {10001L, 20001L, 1411325632683L, 1411325731213L} };

        System.out.println( getMaxConcurrentPool(connections) );

    }





    public static Long getMaxConcurrentPool(Long[][] connections) {

        if (null == connections || 0 == connections.length)
            return null;


        // 先排序，将同一桌面池的数据放到一起
       // Arrays.sort(connections);

        // 最大并发数
        long max_synchrony_number = 0;

        // 最大并发数的桌面池id
        Long max_synchrony_id = null;


        int index = 0;
        List<MyTime> time_list = new LinkedList<MyTime>();
        while (index < connections.length) {
            // 当前桌面池下的最大并发数

            Long current_id = connections[index][1];

            time_list.clear();
            time_list.add( new MyTime(connections[index][2], true) );
            time_list.add( new MyTime(connections[index][3], false) );

            while (++index < connections.length && current_id.equals(connections[index][1])){
                time_list.add( new MyTime(connections[index][2], true) );
                time_list.add( new MyTime(connections[index][3], false) );
            }

            time_list.sort( (t1, t2) -> { return t1.time.compareTo(t2.time); } );

            long current_max_synchromy_number = 0;
            long current_synchromy_number = 0;
            for (MyTime time : time_list) {
                if (time.isStart) {
                    current_synchromy_number++;
                    current_max_synchromy_number = current_synchromy_number > current_max_synchromy_number ? current_synchromy_number : current_max_synchromy_number;
                }

                else {
                    current_synchromy_number--;
                }
            }

            if (current_max_synchromy_number > max_synchrony_number) {
                max_synchrony_number = current_max_synchromy_number;
                max_synchrony_id = current_id;
            }

        }


        return max_synchrony_id;
    }


    private static class MyTime {
        public Long time;
        public boolean isStart;         // 当前是开始时间还是结束时间

        public MyTime(Long time, boolean isStart) {
            this.time = time;
            this.isStart = isStart;
        }

    }

}
