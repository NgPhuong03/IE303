package ThucHanh;

public class bai2 {
    public static double TinhSoPi() {
        int n = 100000, diem_random = 0, r = 1;
        for (int i = 0; i < n; i++) {
            double x = (Math.random() * 2 - 1);
            double y = (Math.random() * 2 - 1);
            if (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r, 2)) {
                diem_random++;
            }
        }
        double Pi = 4 * (double) diem_random / n;
        return Pi;
    }

    public static void main(String[] args) {
        System.out.println("Gia tri xap xi cua Pi: " + TinhSoPi());
    }
}
