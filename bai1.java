package ThucHanh;

import java.util.Scanner;

public class bai1 {
    public static double TinhDienTich(int r) {
        int n = 100000;
        int diem_random = 0;
        for (int i = 0; i < n; i++) {
            double x = r * (Math.random() * 2 - 1);
            double y = r * (Math.random() * 2 - 1);
            if (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r, 2)) {
                diem_random++;
            }
        }
        double ty_le = (double) diem_random / n;
        double S = 4 * Math.pow(r, 2) * ty_le;
        return S;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hay nhap vao ban kinh hinh tron: ");
        int r = sc.nextInt();
        double S = TinhDienTich(r);
        System.out.println("Dien tich hinh tron xap xi : " + S);
        sc.close();
    }
}