import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class PlayGround {
    public static void main(final String[] args) {
        KthLargest kthLargest = new KthLargest(3, new int[]{4, 5, 8, 2});
        kthLargest.add(3);   // return 4
        kthLargest.add(5);   // return 5
        kthLargest.add(10);  // return 5
        kthLargest.add(9);   // return 8
        kthLargest.add(4);   // return 8
    }
    static class KthLargest {
        final PriorityQueue<Integer> pq;
        int k;
        public KthLargest(int k, int[] nums) {
            this.k = k;
            pq  = new PriorityQueue<>();
            for (int i = 0; i < nums.length; i++) {
                pq.add(nums[i]);
            }
            while(pq.size() > k) {
                pq.poll();
            }
        }

        public int add(int val) {
            pq.add(val);
            if (pq.size() > this.k) {
                pq.poll();
            }

            return pq.peek();
        }
    }

    class Solution {
        public int lastStoneWeight(int[] stones) {
            final PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
            for (int i = 0; i < stones.length; i++) {
                pq.add(stones[i]);
            }
            while(pq.size() >= 2) {
                Integer y = pq.poll();
                Integer x = pq.poll();
                if (x.equals(y)) {
                    // do nothing
                } else{
                    pq.add(y - x);
                }
            }
            if (pq.size() == 0) {
                return 0;
            }
            return pq.poll();
        }
    }
}