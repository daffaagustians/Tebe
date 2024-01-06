import java.util.Scanner;

public interface Penjualan {
    void lihatRiwayatPenjualan();
    void tambahDataPenjualan(Scanner scanner);
    void editDataPenjualan(Scanner scanner);
    void hapusDataPenjualan(Scanner scanner);
    void cariDataPenjualan(Scanner scanner);
}
