// Superclass
public class Produk {
    private int id;
    private String jenis;
    private double harga;
    private int jumlah;

    public Produk(int id, String jenis, double harga, int jumlah) {
        this.id = id;
        this.jenis = jenis;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    // Getter dan Setter untuk atribut id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter dan Setter untuk atribut jenis
    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    // Getter dan Setter untuk atribut harga
    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    // Getter dan Setter untuk atribut jumlah
    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
