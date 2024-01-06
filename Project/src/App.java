import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BakpaoManager bakpaoManager = new BakpaoManager();
        
        while (true) {
            System.out.print("\033[H\033[2J"); // Membersihkan terminal
            System.out.flush(); // Memastikan output selesai diproses sebelum membersihkan layar
            System.out.println("Menu:");
            System.out.println("1. Lihat Riwayat Penjualan");
            System.out.println("2. Tambah Data Penjualan");
            System.out.println("3. Update Data Penjualan");
            System.out.println("4. Hapus Data Penjualan");
            System.out.println("5. Cari Data Penjualan");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu: ");
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    bakpaoManager.lihatRiwayatPenjualan();
                    break;
                case 2:
                    bakpaoManager.tambahDataPenjualan(scanner);
                    break;
                case 3:
                    bakpaoManager.editDataPenjualan(scanner);
                    break;
                case 4:
                    bakpaoManager.hapusDataPenjualan(scanner);
                    break;
                case 5:
                    bakpaoManager.cariDataPenjualan(scanner);
                    break;
                case 6:
                    System.out.println("Terima kasih!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
                    break;
            }

            System.out.println("\nTekan 'Enter' untuk melanjutkan...");
            scanner.nextLine(); // Menunggu 'Enter' sebelum membersihkan layar
            scanner.nextLine(); // Mengonsumsi 'Enter'
        }
    }
}
