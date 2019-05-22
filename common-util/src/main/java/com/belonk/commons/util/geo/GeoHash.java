package com.belonk.commons.util.geo;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;

/**
 * Geohash算法工具类。
 * <br><br>
 * </ol>
 * <b>Geohash算法基本原理：</b>
 * <p>
 * 下面以北海公园为例介绍GeoHash算法的计算步骤：
 * <ol>
 * <li>
 * 根据经纬度计算GeoHash二进制编码
 * <br><br>
 * 地球纬度区间是[-90,90]， 北海公园的纬度是39.928167，可以通过下面算法对纬度39.928167进行逼近编码:
 * <p>
 * 1）区间[-90,90]进行二分为[-90,0),[0,90]，称为左右区间，可以确定39.928167属于右区间[0,90]，给标记为1；
 * <p>
 * 2）接着将区间[0,90]进行二分为 [0,45),[45,90]，可以确定39.928167属于左区间 [0,45)，给标记为0；
 * <p>
 * 3）递归上述过程39.928167总是属于某个区间[a,b]。随着每次迭代区间[a,b]总在缩小，并越来越逼近39.928167；
 * <p>
 * 4）如果给定的纬度x（39.928167）属于左区间，则记录0，如果属于右区间则记录1，这样随着算法的进行会产生一个序列1011100，序列的长度跟给
 * 定的区间划分次数有关。
 * </li>
 * <li>
 * 二进制编码组码
 * <p>
 * <p>
 * 通过上述计算，纬度产生的编码为10111 00011，经度产生的编码为11010 01011。<b>偶数位放经度，奇数位放纬度</b>，把2串编码组合生成新串：
 * 11100 11101 00100 01111。
 * </li>
 * <li>
 * 二进制编码进行base32编码
 * <p>
 * 最后使用用0-9、b-z（去掉a, i, l, o）这32个字母进行base32编码，首先将11100 11101 00100 01111转成十进制，对应着28、29、4、15，十
 * 进制对应的编码就是wx4g。同理，将编码转换成经纬度的解码算法与之相反，具体不再赘述。
 * </li>
 * </ol>
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 */
public class GeoHash {
    private static int numbits = 6 * 5;
    private final static char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private final static HashMap<Character, Integer> LOOKUP = new HashMap<Character, Integer>();

    static {
        int i = 0;
        for (char c : DIGITS) {
            LOOKUP.put(c, i++);
        }
    }

    public static void main(String[] args) throws Exception {
        String wx4g0ec1ezk4 = GeoHash.encode(39.92324, 116.3907);
        System.out.println(wx4g0ec1ezk4);
        System.out.println("decode : " + Arrays.toString(GeoHash.decode(wx4g0ec1ezk4)));
        System.out.println(GeoHash.isInRange(wx4g0ec1ezk4, 39.92324, 116.3907, 8));
        System.out.println(GeoHash.isInRange(wx4g0ec1ezk4, 39.92323, 116.3909, 9));

        String wx4g0ec19x3d = GeoHash.encode(39.92324, 116.3906);
        System.out.println(wx4g0ec19x3d);
        System.out.println("decode : " + Arrays.toString(GeoHash.decode(wx4g0ec19x3d)));

        String wx4g0ebcxpr4 = GeoHash.encode(39.92324, 116.3905);
        System.out.println(wx4g0ebcxpr4);
        System.out.println("decode : " + Arrays.toString(GeoHash.decode(wx4g0ebcxpr4)));

        String wx4g0ebcszk6 = GeoHash.encode(39.92324, 116.3904);
        System.out.println(wx4g0ebcszk6);
        System.out.println("decode : " + Arrays.toString(GeoHash.decode(wx4g0ebcszk6)));
        ;
        String wx4g0ec19t3x = GeoHash.encode(39.92323, 116.3906);
        System.out.println(wx4g0ec19t3x);
        System.out.println("decode : " + Arrays.toString(GeoHash.decode(wx4g0ec19t3x)));

        String wx4g0ec1evkp = GeoHash.encode(39.92323, 116.3907);
        System.out.println(wx4g0ec1evkp);
        System.out.println("decode : " + Arrays.toString(GeoHash.decode(wx4g0ec1evkp)));

        String wx4g0ec1wjqz = GeoHash.encode(39.92323, 116.3908);
        System.out.println(wx4g0ec1wjqz);
        System.out.println("decode : " + Arrays.toString(GeoHash.decode(wx4g0ec1wjqz)));
    }

    /**
     * 将geohash值解码为坐标。
     *
     * @param geohash geohash值
     * @return 坐标数组，第0个表示纬度，第1个表示经度
     */
    public static double[] decode(String geohash) {
        StringBuilder buffer = new StringBuilder();
        for (char c : geohash.toCharArray()) {
            int i = LOOKUP.get(c) + 32;
            buffer.append(Integer.toString(i, 2).substring(1));
        }

        BitSet lonset = new BitSet();
        BitSet latset = new BitSet();

        // even bits
        int j = 0;
        for (int i = 0; i < numbits * 2; i += 2) {
            boolean isSet = false;
            if (i < buffer.length()) {
                isSet = buffer.charAt(i) == '1';
            }
            lonset.set(j++, isSet);
        }

        // odd bits
        j = 0;
        for (int i = 1; i < numbits * 2; i += 2) {
            boolean isSet = false;
            if (i < buffer.length()) {
                isSet = buffer.charAt(i) == '1';
            }
            latset.set(j++, isSet);
        }

        double lon = decode(lonset, -180, 180);
        double lat = decode(latset, -90, 90);

        return new double[]{lat, lon};
    }

    /**
     * 编码给定坐标。
     *
     * @param lat 坐标纬度
     * @param lon 坐标经度
     * @return geohash值
     */
    public static String encode(double lat, double lon) {
        BitSet latbits = getBits(lat, -90, 90);
        BitSet lonbits = getBits(lon, -180, 180);
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < numbits; i++) {
            buffer.append((lonbits.get(i)) ? '1' : '0');
            buffer.append((latbits.get(i)) ? '1' : '0');
        }
        return base32(Long.parseLong(buffer.toString(), 2));
    }

    /**
     * 判断坐标是否处于geoHash值代表的区域范围内，需要明确指定geohash精度。
     * <p>
     * Geohash精度：
     *
     * @param geoHash   geohash值，代表一个区域范围
     * @param lat       纬度
     * @param lon       经度
     * @param precision 给定精度，为4时，范围进度为20千米，为8时，范围精度在19米左右，为9时，范围精度在2米左右
     * @return true - 在范围内，false - 在范围外
     */
    public static Boolean isInRange(String geoHash, Double lat, Double lon, int precision) {
        if (precision <= 0) {
            throw new IllegalArgumentException("Precision must be defined.");
        }
        String nowHash = encode(lat, lon).substring(0, precision);
        return geoHash.substring(0, precision).equals(nowHash);
    }

    private static double decode(BitSet bs, double floor, double ceiling) {
        double mid = 0;
        for (int i = 0; i < bs.length(); i++) {
            mid = (floor + ceiling) / 2;
            if (bs.get(i)) {
                floor = mid;
            } else {
                ceiling = mid;
            }
        }
        return mid;
    }

    private static BitSet getBits(double lat, double floor, double ceiling) {
        BitSet buffer = new BitSet(numbits);
        for (int i = 0; i < numbits; i++) {
            double mid = (floor + ceiling) / 2;
            if (lat >= mid) {
                buffer.set(i);
                floor = mid;
            } else {
                ceiling = mid;
            }
        }
        return buffer;
    }

    private static String base32(long i) {
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);
        if (!negative) {
            i = -i;
        }
        while (i <= -32) {
            buf[charPos--] = DIGITS[(int) (-(i % 32))];
            i /= 32;
        }
        buf[charPos] = DIGITS[(int) (-i)];

        if (negative) {
            buf[--charPos] = '-';
        }
        return new String(buf, charPos, (65 - charPos));
    }
}
