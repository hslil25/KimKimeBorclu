import java.util.ArrayList;
import java.util.Scanner;

public class Borc {
    static ArrayList<insan> borclular = new ArrayList<>();
    static ArrayList<String> gecmis = new ArrayList<>();
    static Scanner in = new Scanner(System.in);

    public static void hesapGoster() {
        System.out.println(String.format("%-15s%-10s", "İsim","Borç"));
        for(insan i:borclular) {
            System.out.println(String.format("%-15s%-10.2f", i.getName(),i.getBorc()));
        }
    }

    public static void sonDurum() throws CloneNotSupportedException {
        ArrayList<insan> takip = new ArrayList<>();
        for (int i = 0; i < borclular.size(); i++) {
            takip.add((insan) borclular.get(i).clone());
        }
        ArrayList<String> adimlar = new ArrayList<>();
        insan borclu;
        insan alacaklı;
        while (!isAllZero(takip)) {
            borclu = takip.get(findMin(takip));
            alacaklı = takip.get(findMax(takip));
            adimlar.add(borclu.getName() +  alacaklı.getName() + "'a/e " + Math.abs(borclu.getBorc()) + " ₺" + " ver.");
            alacaklı.borcEkle(Math.abs(borclu.getBorc()));
            borclu.borcEkle(borclu.getBorc());
        }
        for(String a:adimlar) {
            System.out.println(a);
        }

    }

    public static boolean isAllZero(ArrayList<insan> list) {
        for (insan insan : list) {
            if (insan.getBorc() != 0)
                return false;
        }
        return true;
    }

    public static int findMin(ArrayList<insan> list) {
        double min = Double.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getBorc() < min) {
                min = list.get(i).getBorc();
                index = i;
            }
        }
        return index;

    }

    public static int findMax(ArrayList<insan> list) {
        double max = Double.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getBorc() > max) {
                max = list.get(i).getBorc();
                index = i;
            }
        }
        return index;

    }

    public static insan stringFinder(String name) {
        for (insan ara : borclular) {
            if (ara.getName().toLowerCase().equals(name.toLowerCase())) {
                return ara;
            }
        }
        return null;
    }

    public static void gecmiseEkle(String ad1, String ad2, double borc) {
        gecmis.add(String.format("%-17s%-17s%-7.2f", ad1, ad2, borc));
    }

    public static void kisiEkle() {
        String cont;
        String ad;
        while (true) {
            System.out.println("\nKişi eklemek istemiyorsanız hayır yazın aksi takdirde enter tuşuna basın.");
            cont = in.nextLine();
            if (cont.equals("break")) {
                return;
            }
            System.out.println("Bu kişinin ismi nedir?");
            ad = in.nextLine();
            borclular.add(new insan(ad, 0));
            System.out.println("\nBaşarıyla eklendi.");
        }
    }

    public static void gecmisGoster() {
        System.out.println(String.format("%-17s%-17s%-7s", "Borçlu".toUpperCase(), "Alacaklı".toUpperCase(),
                "Borç".toUpperCase()));
        for (String olay : gecmis) {
            System.out.println(olay);
        }
    }

    public static void borcEkle() {
        String cont;
        insan borclu;
        insan alacakli;
        double borc;
        boolean durum;
        while (true) {
            System.out.println("\nBorç eklemek istemiyorsanız hayır yazın aksi takdirde enter tuşuna basın.");
            cont = in.nextLine();
            if (cont.equals("break")) {
                return;
            }
            System.out.println("Borçlu kişinin ismi nedir?");
            borclu = stringFinder(in.nextLine());
            System.out.println("Alacakli kişinin ismi nedir?");
            alacakli = stringFinder(in.nextLine());
            if (borclu == null || alacakli == null) {
                System.out.println("Lütfen tekrar deneyin!");
            } else {
                System.out.print("Borç kaç ₺: ");
                borc = in.nextDouble();
                durum = borcKaydiEkle(borclu, alacakli, borc);
                if (durum)
                    System.out.println("\nBorç sisteme başarı ile eklendi");
                else
                    System.out.println("\nBir sorun oluştu.");
            }
            in.nextLine();
        }
    }

    public static boolean borcKaydiEkle(insan borclu, insan alacakli, double borc) {
        if (borc <= 0) {
            return false;
        }
        borclu.borcEkle(borc);
        alacakli.borcEkle(-borc);
        gecmiseEkle(borclu.getName(), alacakli.getName(), borc);
        return true;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        System.out.println("\nKim Kime Borçlu uygulamasına hoş geldiniz!");
        int choice;
        while (true) {
            System.out.println("\n1-Kişi Ekleme");
            System.out.println("2-Borç Kaydı Ekleme");
            System.out.println("3-Geçmişi Göster");
            System.out.println("4-Hesapları Göster");
            System.out.println("5-Son Durumu Göster");
            System.out.println("6-Çıkış");
            System.out.println();
            System.out.print("Seçiminiz: ");
            choice = in.nextInt();
            while (choice > 6 || choice < 1) {
                System.out.println("Geçersiz giriş yaptınız. Lütfen tekrar deneyin!");
                System.out.print("Seçiminiz: ");
                System.out.println();
                choice = in.nextInt();
            }
            in.nextLine();
            switch (choice) {
                case 1:
                    kisiEkle();
                    break;
                case 2:
                    borcEkle();
                    break;
                case 3:
                    gecmisGoster();
                    break;
                case 4:
                    hesapGoster();
                    break;
                case 5:
                try {
                    sonDurum();
                } catch (Exception CloneNotSupportedException) {
                    System.out.println("Bir hata oluştu!");
                } 
                    break;
                case 6:
                    return;
            }
        }
    }
}
