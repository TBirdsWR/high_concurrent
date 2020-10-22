import java.util.Arrays;

public class HCPX {


    public static void main(String[] args) {
        int[] ints = new int[]{24,2,5,6,1,7,43,76,3,123445,6,87,432,12,33454,612,74,833,98,4444};

//        int num =


        px(ints);

        System.out.println(Arrays.toString(ints));
    }

    private static void px(int[] ints) {
        for (int i = 0; i < ints.length; i++) {
            for (int j = i + 1; j < ints.length; j++) {
                if(ints[i] > ints[j]){
                    int temp = ints[j];
                    ints[j] = ints[i];
                    ints[i] = temp;
                }


            }
        }
    }



}
