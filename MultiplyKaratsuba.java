import java.util.Random;
import java.math.BigInteger;

public class Multiply {

    public static long getNumberOfBits(long a) {
        String s = Long.toBinaryString(a);
        return s.length();
    }

    public static long getLower(long a, int m) {

        return (long) (a % Math.pow(2, m));

    }

    public static long getHigher(long a, int m) {

        return (long) (a/Math.pow(2,m));
    }

    public static BigInteger getHigher(BigInteger a, int m) {
        return a.divide(new BigInteger("2").pow(m));
    }

    public static BigInteger getLower(BigInteger a, int m) {
        return a.mod(new BigInteger("2").pow(m));
    }

    public static long multiply(long a, long b) {

        long aLen = getNumberOfBits(a);
        long bLen = getNumberOfBits(b);

        // Set n to the greater of the number of digits in a and b
        long n = aLen > bLen ? aLen : bLen;

        int threshold = 4;

        if (a == 0 || b == 0) {
            return 0;
        } else if (n <= threshold){
            return  a*b;
        }

        int m = (int)n/2;
        long p = getHigher(a, m);
        long q = getLower(a, m);
        long r = getHigher(b, m);
        long s = getLower(b, m);

        long pr = multiply(p,r);
        long ps = multiply(p,s);
        long qr = multiply(q,r);
        long qs = multiply(q,s);

        long ans = (long) (pr * Math.pow(2, 2*m) + (ps + qr) * Math.pow(2, m) + qs);

        return ans;
    }

    public static BigInteger multiply(BigInteger a, BigInteger b) {
        int aLen = a.bitCount();
        int bLen = b.bitCount();

        // Set n to the greater of the number of digits in a and b
        int n = aLen > bLen ? aLen : bLen;

        int threshold = 4;

        if (a.equals(0) || b.equals(0)) {
            return new BigInteger("0");
        } else if (n <= threshold){
            return  a.multiply(b);
        }

        int m = n/2;
        BigInteger p = getHigher(a, m);
        BigInteger q = getLower(a, m);
        BigInteger r = getHigher(b, m);
        BigInteger s = getLower(b, m);

        BigInteger pr = multiply(p,r);
        BigInteger ps = multiply(p,s);
        BigInteger qr = multiply(q,r);
        BigInteger qs = multiply(q,s);

        BigInteger ans = BigInteger.ZERO;
        ans = ans.add(pr);
        ans = ans.multiply(new BigInteger(String.valueOf((int)Math.pow(2,2*m))));
        ans = ans.add(qs);

        BigInteger tmp = new BigInteger(ps.toString());
        tmp = tmp.add(qr);
        tmp = tmp.multiply(new BigInteger(String.valueOf((int)Math.pow(2,m))));

        ans = ans.add(tmp);

        return ans;
    }

    public static long multiplyFast(long a, long b) {

        long aLen = getNumberOfBits(a);
        long bLen = getNumberOfBits(b);

        long n = aLen > bLen ? aLen : bLen;

        int threshold = 4;

        if (a == 0 || b == 0) {
            return  0;
        } else if (n <= threshold) {
            return a*b;
        }

        int m = (int)n/2;

        long p = getHigher(a, m);
        long q = getLower(a,m);
        long r = getHigher(b,m);
        long s = getLower(b,m);

        long u = multiplyFast(p,r);
        long w = multiplyFast(q,s);

        // For this multiplication, you can end up with negative numbers which do not split
        // properly in 2s compliment binary
        // To get around this we change the negative numbers to positive, and back if relevant
        long x = q-p;
        long y = s-r;
        long v;

        // If both numbers are negative, we can flip them and multiply as normal
        if (x < 0 && y < 0) {
            x *= -1;
            y *= -1;

            v = multiplyFast(x, y);

        // If one the the numbers if negative, flip it, multiply, then flip the result

        } else if (x < 0 && y >= 0) {
            x *= -1;

            v = multiplyFast(x, y);

            v *= -1;
        } else if (x >= 0 && y < 0) {
            y *= -1;

            v = multiplyFast(x, y);

            v *= -1;

        // The rest of the time we can simply multiply and leave it
        } else {
            v = multiplyFast(x, y);
        }

        long ans = (long)(u * Math.pow(2, 2*m) + (u + w - v) * Math.pow(2,m) + w);
        return ans;
    }

    public static BigInteger multiplyFast (BigInteger a, BigInteger b) {
        int aLen = a.bitCount();
        int bLen = b.bitCount();

        int n = aLen > bLen ? aLen : bLen;

        int threshold = 4;

        if (a.equals(0) || b.equals(0)) {
            return  new BigInteger("0");
        } else if (n <= threshold) {
            return a.multiply(b);
        }

        int m = n/2;

        BigInteger p = getHigher(a, m);
        BigInteger q = getLower(a,m);
        BigInteger r = getHigher(b,m);
        BigInteger s = getLower(b,m);

        BigInteger u = multiplyFast(p,r);
        BigInteger w = multiplyFast(q,s);

        // For this multiplication, you can end up with negative numbers which do not split
        // properly in 2s compliment binary
        // To get around this we change the negative numbers to positive, and back if relevant
        BigInteger x = q.subtract(p);
        BigInteger y = s.subtract(r);
        BigInteger v;

        // If both numbers are negative, we can flip them and multiply as normal
        if (x.compareTo(new BigInteger("0")) == -1 && y.compareTo(new BigInteger("0")) == -1) {
            x = x.abs();
            y = x.abs();

            v = multiplyFast(x, y);

            // If one the the numbers if negative, flip it, multiply, then flip the result

        } else if (x.compareTo(new BigInteger("0")) == -1 && (y.equals(0) || y.compareTo(new BigInteger("0")) == 1)) {
            x = x.abs();

            v = multiplyFast(x, y);

            v = v.multiply(new BigInteger("-1"));
        } else if ((x.equals(0) || x.compareTo(new BigInteger("0")) == 1) && y.compareTo(new BigInteger("0")) == -1) {
            y = y.abs();

            v = multiplyFast(x, y);

            v = v.multiply(new BigInteger("-1"));

            // The rest of the time we can simply multiply and leave it
        } else {
            v = multiplyFast(x, y);
        }

        // Perform the BigInteger equivalents to the arithmetic operations
        BigInteger ans = new BigInteger(u.toString());
        ans = ans.multiply(new BigInteger(String.valueOf((int)Math.pow(2,2*m))));
        ans = ans.add(w);

        BigInteger tmp = new BigInteger(u.toString());
        tmp = tmp.add(w);
        tmp = tmp.subtract(v);
        tmp = tmp.multiply(new BigInteger(String.valueOf((int)Math.pow(2,m))));

        ans = ans.add(tmp);

        return ans;
    }

    public static void test() {
        Random r = new Random(20010);

        for (int n = 5; n < Integer.MAX_VALUE-2; n++) {
            BigInteger a = new BigInteger(n, r);
            BigInteger b = new BigInteger(n, r);

            long begin = System.nanoTime();
            multiplyFast(a, b);
            long end = System.nanoTime();
            System.out.println(n + ", " + (end-begin));
        }
    }

    public static void testLong() {
        Random r = new Random(20010);

        for (int n = 5; n < Integer.MAX_VALUE-2; n++) {
            BigInteger a = new BigInteger(n, r);
            BigInteger b = new BigInteger(n, r);

            long begin = System.nanoTime();
            multiply(a, b);
            long end = System.nanoTime();
            System.out.println(n + ", " + (end-begin));
        }
    }

    // Main consists of tes
    public static void main(String[] args) {

        Random r = new Random();
        r.setSeed(12356);

        for (int n = 2; n < 32; n++) {
            // Minimum and maximum ints with n bits
            int min = (int)Math.pow(2, n - 1);
            int max = (int)Math.pow(2, n) - 1;

            // Define a and b to be random numbers between the min and max numbers of n bits
            int a = r.nextInt(max - min + 1) + min;
            int b = r.nextInt(max - min + 1) + min;

            // Execute and time the function
            long begin = System.nanoTime();
            multiply(a,b);
            long end = System.nanoTime();
            System.out.println(n + ", " + (end-begin));
        }

        long a = 12345678L;
        long b = 87654321L;
        long res = multiplyFast(a,b);

        System.out.println(a + " x " + b + " = " + (a*b) + "\nMy answer: " + res);

        BigInteger aBig = new BigInteger(16, r);
        BigInteger bBig = new BigInteger(16, r);
        System.out.println("a: " + aBig + " b: " + bBig);
        System.out.println(multiplyFast(aBig,bBig).toString());

        System.exit(0);
    }
}
