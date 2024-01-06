import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.sql.*;


public class BakpaoManager implements Penjualan {
    private Connection connection;
    private List<Bakpao> daftarPenjualan;
    
    public BakpaoManager() {
        try {
            // Menghubungkan ke database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/pbo", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        daftarPenjualan = new ArrayList<>();
    }

    public double hitungTotalHarga() {
        double totalHarga = 0;
        for (Bakpao bakpao : daftarPenjualan) {
            totalHarga += bakpao.getHarga() * bakpao.getJumlah();
        }
        return totalHarga;
    }

    @Override
    public void lihatRiwayatPenjualan() {
    try {
        // Memastikan koneksi sudah terbuka
        if (connection == null || connection.isClosed()) {
            System.out.println("Koneksi ke database tidak tersedia.");
            return;
        }

        // Mengambil riwayat penjualan dari database
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tebe");
        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.isBeforeFirst()) {
            System.out.println("Tidak ada riwayat penjualan.");
        } else {
            System.out.println("Riwayat Penjualan Bakpao:");
            System.out.println("-----------------------------------------------------------------------------------------------");
            System.out.printf("| %-4s | %-12s | %-15s | %-18s | %-10s | %-6s | %-8s |\n",
                    "ID", "Pelanggan", "Jenis", "Tanggal", "Harga", "Jumlah", "Status");
            System.out.println("-----------------------------------------------------------------------------------------------");
            double totalHarga = 0;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String namaPelanggan = resultSet.getString("pelanggan");
                String jenis = resultSet.getString("jenis");
                Date tanggal = resultSet.getDate("tanggal");
                double harga = resultSet.getDouble("harga");
                int jumlah = resultSet.getInt("jumlah");
                String status = resultSet.getString("status");

                System.out.printf("| %-4d | %-12s | %-15s | %-18s | %-10.2f | %-6d | %-8s |\n",
                        id, namaPelanggan, jenis, tanggal, harga, jumlah, status);
                totalHarga += harga * jumlah;
            }
            System.out.println("-----------------------------------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.printf("| %-68s | %-10.2f |\n", "Total Penjualan Hari Ini", totalHarga);
            System.out.println("-------------------------------------------------------------------------------------");
        }

        // Menutup statement dan resultSet setelah selesai digunakan
        statement.close();
        resultSet.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    @Override
    public void tambahDataPenjualan(Scanner scanner) {
    try {
        
        System.out.println("Masukkan ID:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Membuang karakter newline
        System.out.println("Masukkan Nama Pelanggan:");
        String namaPelanggan = scanner.nextLine();
        System.out.println("Masukkan Jenis Bakpao:");
        String jenis = scanner.nextLine();
        System.out.println("Masukkan Harga:");
        double harga = scanner.nextDouble();
        System.out.println("Masukkan Jumlah:");
        int jumlah = scanner.nextInt();
        scanner.nextLine(); 

        System.out.println("Masukkan Status (menunggu/selesai):");
        String status = scanner.nextLine();

        // Mendapatkan tanggal hari ini
        Date tanggal = new Date();

        // Memasukkan data ke database menggunakan PreparedStatement
        String query = "INSERT INTO tebe (ID, Pelanggan, Jenis, Tanggal, Harga, Jumlah, Status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, namaPelanggan);
        preparedStatement.setString(3, jenis);
        preparedStatement.setDate(4, new java.sql.Date(tanggal.getTime()));
        preparedStatement.setDouble(5, harga);
        preparedStatement.setInt(6, jumlah);
        preparedStatement.setString(7, status);

        preparedStatement.executeUpdate();
        System.out.println("Data penjualan berhasil ditambahkan ke database.");
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    
}


    @Override
    public void editDataPenjualan(Scanner scanner) {
    try {
        
        System.out.println("Masukkan ID data yang ingin diubah:");
        int idToEdit = scanner.nextInt();
        scanner.nextLine(); // Membersihkan karakter newline

        // Memeriksa apakah data dengan ID yang dimasukkan ada di database
        PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM tebe WHERE id = ?");
        checkStatement.setInt(1, idToEdit);
        ResultSet resultSet = checkStatement.executeQuery();

        if (!resultSet.isBeforeFirst()) {
            System.out.println("Data dengan ID tersebut tidak ditemukan.");
            return;
        }

        System.out.println("Masukkan Jenis Baru:");
        String jenisBaru = scanner.nextLine();
        System.out.println("Masukkan Harga Baru:");
        double hargaBaru = scanner.nextDouble();
        System.out.println("Masukkan Jumlah Baru:");
        int jumlahBaru = scanner.nextInt();
        scanner.nextLine(); // Membersihkan karakter newline

        System.out.println("Masukkan Status Baru (menunggu/selesai):");
        String statusBaru = scanner.nextLine();

        // Mengubah data penjualan di database
        PreparedStatement updateStatement = connection.prepareStatement("UPDATE tebe SET jenis = ?, harga = ?, jumlah = ?, status = ? WHERE id = ?");
        updateStatement.setString(1, jenisBaru);
        updateStatement.setDouble(2, hargaBaru);
        updateStatement.setInt(3, jumlahBaru);
        updateStatement.setString(4, statusBaru);
        updateStatement.setInt(5, idToEdit);

        int rowsAffected = updateStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Data berhasil diubah.");
        } else {
            System.out.println("Gagal mengubah data.");
        }

        // Menutup statement dan resultSet setelah selesai digunakan
        checkStatement.close();
        updateStatement.close();
        resultSet.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    @Override
    public void hapusDataPenjualan(Scanner scanner) {
    try {
        
        System.out.println("Masukkan ID data yang ingin dihapus:");
        int idToDelete = scanner.nextInt();

        // Memeriksa apakah data dengan ID yang dimasukkan ada di database
        PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM tebe WHERE id = ?");
        checkStatement.setInt(1, idToDelete);
        ResultSet resultSet = checkStatement.executeQuery();

        if (!resultSet.isBeforeFirst()) {
            System.out.println("Data dengan ID tersebut tidak ditemukan.");
            return;
        }

        // Menghapus data penjualan dari database
        PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM tebe WHERE id = ?");
        deleteStatement.setInt(1, idToDelete);

        int rowsAffected = deleteStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Data berhasil dihapus.");
        } else {
            System.out.println("Gagal menghapus data.");
        }

        // Menutup statement dan resultSet setelah selesai digunakan
        checkStatement.close();
        deleteStatement.close();
        resultSet.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

@Override
public void cariDataPenjualan(Scanner scanner) {
    try {
        System.out.println("Masukkan ID data yang ingin dicari:");
        int idToSearch = scanner.nextInt();

        // Membersihkan karakter newline setelah pembacaan integer
        scanner.nextLine();

        // Mencari data berdasarkan ID di database
        PreparedStatement searchStatement = connection.prepareStatement("SELECT * FROM tebe WHERE id = ?");
        searchStatement.setInt(1, idToSearch);
        ResultSet resultSet = searchStatement.executeQuery();

        if (!resultSet.isBeforeFirst()) {
            System.out.println("Data dengan ID tersebut tidak ditemukan.");
            return;
        }

        System.out.println("Data ditemukan:");
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-12s | %-15s | %-18s | %-10s | %-6s | %-10s |\n",
                "ID", "Pelanggan", "Jenis", "Tanggal", "Harga", "Jumlah", "Status");
        System.out.println("------------------------------------------------------------------------------------------------");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String namaPelanggan = resultSet.getString("Pelanggan");
            String jenis = resultSet.getString("jenis");
            Date tanggal = resultSet.getDate("tanggal");
            double harga = resultSet.getDouble("harga");
            int jumlah = resultSet.getInt("jumlah");
            String status = resultSet.getString("status"); // Ambil nilai status dari database

            System.out.printf("| %-4d | %-12s | %-15s | %-18s | %-10.2f | %-6d | %-10s |\n",
                    id, namaPelanggan, jenis, tanggal, harga, jumlah, status);
        }
        System.out.println("------------------------------------------------------------------------------------------------");

        // Menutup statement dan resultSet setelah selesai digunakan
        searchStatement.close();
        resultSet.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
