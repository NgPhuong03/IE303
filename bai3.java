package ThucHanh;

import java.util.Scanner;
import java.util.Stack;

class toado {
    int x, y;

    toado(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void xuat() {
        System.out.println(x + " " + y);
    }
}

public class bai3 {
    static void Nhap(toado[] arr, Scanner sc) {
        for (int i = 0; i < arr.length; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            arr[i] = new toado(x, y);
        }
    }

    static void Xuat(toado[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i].xuat();
        }
    }

    // Dinh huong 3 diem
    static int orientation(toado p, toado q, toado r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0)
            return 0; // thẳng hàng
        return (val > 0) ? 1 : 2; // 1 là quay theo chiều kim đồng hồ, 2 là quay ngược chiều kim đồng hồ
    }

    // Khoang cach binh phuong
    static int TinhKhoangCach2(toado p1, toado p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }

    static boolean Sosanh2diem(toado[] a, int p1, int p2) {
        int huong = orientation(a[0], a[p1], a[p2]);
        if (huong == 0) {
            return TinhKhoangCach2(a[0], a[p2]) - TinhKhoangCach2(a[0], a[p1]) > 0;
        }
        return (huong == 2);
    }

    static void SapXepMang(toado[] a) {
        // Dua diem thap nhat ben trai ve dau mang
        int min = 0;
        for (int i = 1; i < a.length; i++) {
            if (a[i].y < a[min].y || a[i].y == a[min].y && a[i].x < a[min].x) {
                min = i;
            }
        }
        toado tmp = a[0];
        a[0] = a[min];
        a[min] = tmp;

        for (int i = 1; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (!Sosanh2diem(a, i, j)) {
                    tmp = a[i];
                    a[i] = a[j];
                    a[j] = tmp;
                }
            }
        }

    }

    static toado nextToTop(Stack<toado> s) {
        toado x = s.pop();
        toado result = s.peek();
        s.push(x);
        return result;
    }

    public static void convexhull(toado[] a) {
        // Loai bo cac diem thang hang
        int m = 1;
        for (int i = 1; i < a.length; i++) {
            while (i < a.length - 1 && orientation(a[0], a[i], a[i + 1]) == 0) {
                i++;
            }
            a[m] = a[i];
            m++;
        }

        if (m < 3) {
            System.out.println("Khong the hinh thanh cac tram bao quanh");
            return;
        }

        Stack<toado> S = new Stack<>();
        S.push(a[0]);
        S.push(a[1]);
        S.push(a[2]);

        for (int i = 3; i < m; i++) {
            while (S.size() > 1 && orientation(nextToTop(S), S.peek(), a[i]) != 2) {
                S.pop();
            }
            S.push(a[i]);
        }

        // In kết quả
        while (!S.isEmpty()) {
            toado p = S.pop();
            p.xuat();
        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap vao so luong tram: ");
        int n = sc.nextInt();
        toado[] arr = new toado[n];
        System.out.println("Nhap vao toa do cua tung tram");
        Nhap(arr, sc);

        // Sap xep mang theo thu tu kim dong ho
        SapXepMang(arr);
        convexhull(arr);

        sc.close();
    }
}
