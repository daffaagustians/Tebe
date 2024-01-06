import java.util.Date;

public class Bakpao extends Produk {
    private String namaPelanggan;
    private Date tanggal;

    public Bakpao(int id, String jenis, double harga, int jumlah, String namaPelanggan, Date tanggal) {
        super(id, jenis, harga, jumlah);
        this.namaPelanggan = namaPelanggan;
        this.tanggal = tanggal;
    }

    // Getter dan Setter untuk atribut namaPelanggan
    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    // Getter dan Setter untuk atribut tanggal
    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }
}