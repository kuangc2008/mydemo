package com.sheep.start_study;

/**
 * Created by kuangcheng on 2014/12/29.
 */
public class HealthStoreUtils {

    public static void main(String[] args) {
        //5费
        fiveAnim();

        //6费
        sixAnim(1);

        //抽牌
        choupai();


        int numberPai = 5;
        float total = 1;
        for(int i=0; i<numberPai; i++) {
            total = total * (27 - i);
            total = total/(i+1);
        }
        float total2 = (float)27 * 26 * 25 * 24 * 23/(5 * 4 * 3 * 2);
        System.out.print(" " +  total + "  " + total2);
        System.out.println();
    }


    private static void choupai() {
//        int total = 27 * 26 * 25 * 24 * 23;
//
//
//        for(int i=0; i<12; i++) {
//            int noFive = (27 - i) * (26 - i) * (25 - i) * (24 -i) *(23 -i);
//            float noGaiLv = (float)noFive / total;
//            System.out.println("有" + i + "个5费生物，则在5费时抽取到至少一个5费的概率是：" + (1 - noGaiLv));
//        }
    }

    private static void fiveAnim() {
        float total = (float)27 * 26 * 25 * 24 * 23/(5 * 4 * 3 * 2);


        for(int i=0; i<12; i++) {
            float noFive = (float)(27 - i) * (26 - i) * (25 - i) * (24 -i) *(23 -i) / ( 5 * 4*3*2*1);
            float noGaiLv = (float)noFive / total;
            System.out.print("有" + i + "个5费生物，则在5费时抽取到至少一个5费的概率是：" + (1 - noGaiLv));
            float _1Five = i *(float) (27- i) *(26-i) * (25 -i) *(24-i) / (4 * 3*2*1);
            float _2Five = i*(i-1) * (27-i) * (26-i) *(25-i) / (3 * 2 * 1 * 2);
            float _3Five = i*(i-1)*(i-2) * (27-i) * (26-i)/ ( 2 * 1 * 3*2);
            float _4Five = i*(i-1) *(i-2)*(i-3) *(float) (27-i)  / (4 * 3 *2 * 1);
            float _5Five = (float)i*(i-1) *(i-2)*(i-3)*(i-4) / (5 * 4 * 3 * 2);

            System.out.print(" ，其中：  1：" + (_1Five/total) + "  2:" + (_2Five/total) + " 3;" + (_3Five/total)
                    +"  4:" + (_4Five/total) +" 5:" + (_5Five/total));

            System.out.println();
        }
    }



    private static void sixAnim(int numberPai) {
//        float total = (float)27 * 26 * 25 * 24 * 23/(5 * 4 * 3 * 2);
//
//
//        for(int i=0; i<12; i++) {
//            float noFive = (float)(27 - i) * (26 - i) * (25 - i) * (24 -i) *(23 -i) / ( 5 * 4*3*2*1);
//            float noGaiLv = (float)noFive / total;
//            System.out.print("有" + i + "个5费生物，则在5费时抽取到至少一个5费的概率是：" + (1 - noGaiLv));
//            float _1Five = i *(float) (27- i) *(26-i) * (25 -i) *(24-i) / (4 * 3*2*1);
//            float _2Five = i*(i-1) * (27-i) * (26-i) *(25-i) / (3 * 2 * 1 * 2);
//            float _3Five = i*(i-1)*(i-2) * (27-i) * (26-i)/ ( 2 * 1 * 3*2);
//            float _4Five = i*(i-1) *(i-2)*(i-3) *(float) (27-i)  / (4 * 3 *2 * 1);
//            float _5Five = (float)i*(i-1) *(i-2)*(i-3)*(i-4) / (5 * 4 * 3 * 2);
//
//            System.out.print(" ，其中：  1：" + (_1Five/total) + "  2:" + (_2Five/total) + " 3;" + (_3Five/total)
//                    +"  4:" + (_4Five/total) +" 5:" + (_5Five/total));
//
//            System.out.println();
//        }
    }
}
