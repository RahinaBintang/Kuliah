import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);
    static String username, password, adminUser, adminPass;
    static int select;
    static int index = 0;
    static boolean error, loop, exit;

    static String[][] dataMahasiswa =  new String[100][4];
    static String[][] nilaiMahasiswa = new String[100][8];

    public static void main(String[] args) {
        System.out.println("\n\t\t\t\t-------------------------");
        System.out.println("\t\t\t\t| Final Project\t\t\t|\n\t\t\t\t| Algoritma Pemrograman |");
        System.out.println("\t\t\t\t-------------------------\n");

        for(int i=0; i<dataMahasiswa.length; i++){
            dataMahasiswa[i][0] = "#";
            nilaiMahasiswa[i][0] = "#";
        }

        readFile();

        do {
            exit = false;
            System.out.println("Menu : ");
            System.out.println("1. Login\n2. Exit");
            System.out.print("Pilih Menu : ");
            select = in.nextInt();
            in.nextLine();
            System.out.println();

            if (select == 1) {
                do {
                    int result = login();
                    if (result == 0) {
                        error = false;
                        System.out.println(" <<< Selamat Datang >>>\n");
                        do {
                            loop = true;
                            menuAdmin();
                        } while (loop);

                    } else if (result == 1) {
                        error = false;
                        System.out.println("<<< Selamat Datang >>>\n");
                        do {
                            loop = true;
                            menuUser();
                        } while (loop);
                    } else {
                        error = true;
                        System.out.println("Gagal Login !\n");
                    }
                } while (error);
            } else if (select == 2) {
                System.out.println("End Process...");
                exit = true;
            } else {
                System.out.println("Perintah Diluar Kemampuann Program !");
            }
            System.out.println();
        }while(!exit);

    }

    // Admin

    static void menuAdmin(){
        readFile();
        System.out.println("Menu : ");
        System.out.println("1. Add Data");
        System.out.println("2. List Data");
        System.out.println("3. Update Data");
        System.out.println("4. Delete Data");
        System.out.println("5. Check Profile");
        System.out.println("6. Exit");
        System.out.print("Pilih Menu : ");
        select = in.nextInt();
        in.nextLine();
        System.out.println();

        if(select == 1){
            addData();
        }
        else if(select == 2){
            displayName();
            System.out.println("1. Cek Detail Data");
            System.out.println("2. Kembali");
            System.out.print("Select Menu : ");
            select = in.nextInt();
            in.nextLine();
            System.out.println();

            if(select == 1){
                String name;
                System.out.print("Nama : ");
                name = in.nextLine();
                System.out.println();
                displayData(name);
            }
            else if(select == 2){
                System.out.println();
                menuAdmin();
            }
        }

        else if(select == 3){
            updateData();
        }
        else if(select == 4){
            String name;
            System.out.print("Nama : ");
            name = in.nextLine();
            deleteData(name);

        }
        else if(select == 5){
            checkProfile();
        }
        else if(select == 6){
            loop = false;
        }

        System.out.println();

    }

    static void addData(){
        readFile();
        String[] data = new String[4];
        String[] nilai = new String[8];
        System.out.print("Nama Mahasiswa  : ");
        data[0] = in.nextLine();
        System.out.print("NIM Mahasiswa   : ");
        data[1] = in.nextLine();
        System.out.print("Usia\t\t\t: ");
        data[2] = in.nextLine();
        System.out.print("Asal Daerah\t\t: ");
        data[3] = in.nextLine();

        System.out.println("\n1. Add Nilai\n2. Kembali");
        System.out.print("Pilih Menu : ");
        select = in.nextInt();
        in.nextLine();
        System.out.println();

        if(select == 1){
            System.out.println("----- Nilai -----");
            System.out.print("Al-Islam dan Kemuhammadiyahan III : ");
            nilai[0] = in.nextLine();
            System.out.print("Algoritma Pemrograman\t\t\t  : ");
            nilai[1] = in.nextLine();
            System.out.print("Grafika Komputer\t\t\t\t  : ");
            nilai[2] = in.nextLine();
            System.out.print("Kewarganegaraan\t\t\t\t\t  : ");
            nilai[3] = in.nextLine();
            System.out.print("Komunikasi Data\t\t\t\t\t  : ");
            nilai[4] = in.nextLine();
            System.out.print("Metode Numerik\t\t\t\t\t  : ");
            nilai[5] = in.nextLine();
            System.out.print("Statistika dan Probabilitas\t\t  : ");
            nilai[6] = in.nextLine();
            System.out.print("Struktur Data\t\t\t\t\t  : ");
            nilai[7] = in.nextLine();

            System.arraycopy(data, 0, dataMahasiswa[index], 0, 4);
            System.arraycopy(nilai, 0, nilaiMahasiswa[index], 0, 8);

        }
        else {
            System.arraycopy(data, 0, dataMahasiswa[index], 0, 4);

            for(int i=0; i<8; i++){
                nilaiMahasiswa[index][i] = "0";
            }
        }
        index++;
        sort();
        saveFile();

        System.out.println("\nData Sukses Ditambahkan !");

    }

    static void displayName(){
        readFile();
        System.out.println("----- Data Mahasiswa -----");
        int i=0;
        while(!dataMahasiswa[i][0].equals("#")){
            System.out.println((i+1) + ". " + dataMahasiswa[i][0]);
            i++;
        }
        System.out.println("-------------------------");
    }

    static void displayData(String name){
        readFile();
        int i = 0;
        while(!dataMahasiswa[i][0].equals("#")){
            if(dataMahasiswa[i][0].equals(name)){
                System.out.println("----------------------------------------");
                System.out.println("Nama\t\t: " + dataMahasiswa[i][0]);
                System.out.println("NIM\t\t\t: " + dataMahasiswa[i][1]);
                System.out.println("Usia\t\t: " + dataMahasiswa[i][2] + " Tahun");
                System.out.println("Asal Daerah\t: " + dataMahasiswa[i][3]);
                System.out.println("----------------------------------------");

                System.out.println("\n---------------- Nilai ----------------");
                System.out.println("Al-Islam dan Kemuhammadiyahan III : " + nilaiMahasiswa[i][0]);
                System.out.println("Algoritma Pemrograman\t\t\t  : " + nilaiMahasiswa[i][1]);
                System.out.println("Grafika Komputer\t\t\t\t  : " + nilaiMahasiswa[i][2]);
                System.out.println("Kewarganegaraan\t\t\t\t\t  : " + nilaiMahasiswa[i][3]);
                System.out.println("Komunikasi Data\t\t\t\t\t  : " + nilaiMahasiswa[i][4]);
                System.out.println("Metode Numerik\t\t\t\t\t  : " + nilaiMahasiswa[i][5]);
                System.out.println("Statistika dan Probabilitas\t\t  : " + nilaiMahasiswa[i][6]);
                System.out.println("Struktur Data\t\t\t\t\t  : " + nilaiMahasiswa[i][7]);
                System.out.println("--------------------------------------");

                break;
            }
            else{
                i++;
            }
        }
    }

    static void updateData(){
        readFile();
        String name;
        int i;
        displayName();
        System.out.print("Nama  : ");
        name = in.nextLine();
        System.out.println();

        System.out.println("1. Update Informasi\n2. Update Nilai\n3. Kembali");
        System.out.print("Pilih Menu : ");
        select = in.nextInt();
        in.nextLine();

        if(select == 1){
            i = 0;
            System.out.println("---------- Update Informasi ----------");
            while(!dataMahasiswa[i][0].equals("#")){
                if(dataMahasiswa[i][0].equals(name)){
                    System.out.print("Nama Baru        : ");
                    dataMahasiswa[i][0] = in.nextLine();
                    System.out.print("NIM Baru         : ");
                    dataMahasiswa[i][1] = in.nextLine();
                    System.out.print("Usia Baru        : ");
                    dataMahasiswa[i][2] = in.nextLine();
                    System.out.print("Asal Daerah Baru : ");
                    dataMahasiswa[i][3] = in.nextLine();
                }
                i++;
            }
            sort();
            saveFile();
            System.out.println("Data Sukses Di-Update !");
        }

        else if(select == 2){
            i = 0;
            System.out.println("------------- Update Nilai -------------");
            while(!dataMahasiswa[i][0].equals("#")){
                if(dataMahasiswa[i][0].equals(name)){
                    System.out.print("Al-Islam dan Kemuhammadiyahan III : ");
                    nilaiMahasiswa[i][0] = in.nextLine();
                    System.out.print("Algoritma Pemrograman\t\t\t  : ");
                    nilaiMahasiswa[i][1] = in.nextLine();
                    System.out.print("Grafika Komputer\t\t\t\t  : ");
                    nilaiMahasiswa[i][2] = in.nextLine();
                    System.out.print("Kewarganegaraan\t\t\t\t\t  : ");
                    nilaiMahasiswa[i][3] = in.nextLine();
                    System.out.print("Komunikasi Data\t\t\t\t\t  : ");
                    nilaiMahasiswa[i][4] = in.nextLine();
                    System.out.print("Metode Numerik\t\t\t\t\t  : ");
                    nilaiMahasiswa[i][5] = in.nextLine();
                    System.out.print("Statistika dan Probabilitas\t\t  : ");
                    nilaiMahasiswa[i][6] = in.nextLine();
                    System.out.print("Struktur Data\t\t\t\t\t  : ");
                    nilaiMahasiswa[i][7] = in.nextLine();
                }
                i++;
            }
            sort();
            saveFile();
            System.out.println("Data Sukses Di-Update !");
        }

        else if(select == 3){
            System.out.println();
            menuAdmin();
        }

    }

    static void deleteData(String name){

        char respon;
        readFile();

        for (int i = 0; !dataMahasiswa[i][0].equals("#"); i++){
            if(dataMahasiswa[i][0].equals(name)){
                System.out.print("Konfirmasi Hapus Data (Y/N) ? : ");
                respon = in.next().charAt(0);

                if(respon == 'Y' || respon == 'y'){
                    for(int j = i; !dataMahasiswa[j][0].equals("#"); j++){
                        dataMahasiswa[j] = dataMahasiswa[j+1];
                        nilaiMahasiswa[j] = nilaiMahasiswa[j+1];
                    }

                    index--;
                    sort();
                    saveFile();
                    System.out.println("Data Sukses Dihapus !");
                }
                break;
            }
        }
    }

    static void checkProfile(){
        readFile();
        System.out.println(" ----- Admin -----");
        System.out.println("Username : " + adminUser);
        System.out.println("Password : " + adminPass);
        System.out.println("-------------------");

        System.out.println("1. Update Data");
        System.out.println("2. Kembali");
        System.out.print("Pilih Menu : ");
        select = in.nextInt();
        in.nextLine();
        System.out.println();

        if(select == 1){
            updateAdmin();
        }
        else if(select == 2){
            System.out.println();
            menuAdmin();
        }
    }

    static void updateAdmin(){
        char respon;
        System.out.println(" ----- Update Menu -----");
        System.out.println("1. Username\n2. Password\n3. Kembali");
        System.out.print("Pilih Menu : ");
        select = in.nextInt();
        in.nextLine();
        System.out.println();

        if(select == 1){
            System.out.print("Username Baru : ");
            username = in.nextLine();
            System.out.print("Konfirmasi Update Username (Y/N) ? ");
            respon = in.next().charAt(0);

            if(respon == 'Y' || respon == 'y'){
                adminUser = username;
                saveFile();
                System.out.println("Username Berhasil Diubah !");
            }
            else {
                System.out.println();
                updateAdmin();
            }
        }
        else if(select == 2){
            System.out.print("Password Baru : ");
            username = in.nextLine();
            System.out.print("Konfirmasi Update Password (Y/N) ? ");
            respon = in.next().charAt(0);

            if(respon == 'Y' || respon == 'y'){
                adminPass = password;
                saveFile();
                System.out.println("Password Berhasil Diubah !");
            }
            else {
                System.out.println();
                updateAdmin();
            }

        }
        else if(select == 3){
            menuAdmin();
        }
        else{
            System.out.println("Perintah Diluar Kemampuan Program !");
            updateAdmin();
        }
    }

    static int login(){
        System.out.println("----- Login -----");
        System.out.print("Username: ");
        username = in.nextLine();
        System.out.print("Password: ");
        password = in.nextLine();
        System.out.println("-----------------\n");

        if(username.equals(adminUser) && password.equals(adminPass)){
            return 0;
        }

        for(int i = 0; !dataMahasiswa[i][0].equals("#"); i++){
            if(username.equals(dataMahasiswa[i][0]) && password.equals(dataMahasiswa[i][1])){
                return 1;
            }
        }

        return -1;
    }

    static int getSize(){
        int size = 0;
        for(int i = 0; !dataMahasiswa[i][0].equals("#"); i++){
            size = i;
        }
        return size;
    }

    static void sort(){
        String[] temp;

        for(int i = getSize()-1; i >= 0; i--){
            for(int j = i; j <= getSize()-1; j++){
                if(dataMahasiswa[j][0].compareTo(dataMahasiswa[j+1][0]) > 0){
                    temp = dataMahasiswa[j];
                    dataMahasiswa[j] = dataMahasiswa[j+1];
                    dataMahasiswa[j+1] = temp;

                    temp = nilaiMahasiswa[j];
                    nilaiMahasiswa[j] = nilaiMahasiswa[j+1];
                    nilaiMahasiswa[j+1] = temp;
                }
            }
        }
    }

    static void readFile(){
        String file1 = "BelajarJava/src/AlgoritmaPemrograman/FinalProject/admin.txt";
        String file2 = "BelajarJava/src/AlgoritmaPemrograman/FinalProject/DataMahasiswa.txt";
        String file3 = "BelajarJava/src/AlgoritmaPemrograman/FinalProject/DataNilai.txt";
        String file4 = "BelajarJava/src/AlgoritmaPemrograman/FinalProject/index.txt";

        File myFile1, myFile2, myFile3, myFile4;

        try {
            myFile1 = new File(file1);
            myFile2 = new File(file2);
            myFile3 = new File(file3);
            myFile4 = new File(file4);

            Scanner read1 = new Scanner(myFile1);
            Scanner read2 = new Scanner(myFile2);
            Scanner read3 = new Scanner(myFile3);
            Scanner read4 = new Scanner(myFile4);


            while(read1.hasNextLine()){
                adminUser = read1.nextLine();
                adminPass = read1.nextLine();
            }

            for(int i=0; read2.hasNextLine(); i++){
                for(int j=0; j<4; j++){
                    if(read2.hasNextLine()){
                        dataMahasiswa[i][j] = read2.nextLine();
                    }
                    else{
                        break;
                    }
                }
            }

            for(int i=0; read3.hasNextLine(); i++){
                for(int j=0; j<8; j++){
                    if(read3.hasNextLine()){
                        nilaiMahasiswa[i][j] = read3.nextLine();
                    }
                    else{
                        break;
                    }
                }
            }


            while(read4.hasNextInt()){
                index = read4.nextInt();
            }

            read1.close();
            read2.close();
            read3.close();
            read4.close();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    static void saveFile(){
        String fileAdminPos = "BelajarJava/src/AlgoritmaPemrograman/FinalProject/admin.txt";
        String fileDataMhs = "BelajarJava/src/AlgoritmaPemrograman/FinalProject/DataMahasiswa.txt";
        String fileDataNilai = "BelajarJava/src/AlgoritmaPemrograman/FinalProject/DataNilai.txt";
        String indexPos = "BelajarJava/src/AlgoritmaPemrograman/FinalProject/index.txt";

        try {
            FileWriter fileAdmin = new FileWriter(fileAdminPos);
            FileWriter fileData = new FileWriter(fileDataMhs);
            FileWriter fileNilai = new FileWriter(fileDataNilai);
            FileWriter fileIndex = new FileWriter(indexPos);

            fileAdmin.write(adminUser + "\n");
            fileAdmin.write(adminPass);

            for(int i = 0; !dataMahasiswa[i][0].equals("#"); i++){
                fileData.write(dataMahasiswa[i][0] +"\n");
                fileData.write(dataMahasiswa[i][1] +"\n");
                fileData.write(dataMahasiswa[i][2] +"\n");
                fileData.write(dataMahasiswa[i][3] +"\n");

                fileNilai.write(nilaiMahasiswa[i][0] +"\n");
                fileNilai.write(nilaiMahasiswa[i][1] +"\n");
                fileNilai.write(nilaiMahasiswa[i][2] +"\n");
                fileNilai.write(nilaiMahasiswa[i][3] +"\n");
                fileNilai.write(nilaiMahasiswa[i][4] +"\n");
                fileNilai.write(nilaiMahasiswa[i][5] +"\n");
                fileNilai.write(nilaiMahasiswa[i][6] +"\n");
                fileNilai.write(nilaiMahasiswa[i][7] +"\n");
            }

            fileIndex.write(String.valueOf(index));

            fileAdmin.close();
            fileData.close();
            fileNilai.close();
            fileIndex.close();

        } catch (IOException e) {
            System.out.println("e.getMessage()");
        }
    }

    // Menu User

    static void menuUser(){
        readFile();
        System.out.println("Menu : ");
        System.out.println("1. Cek Nilai");
        System.out.println("2. Cek Profil");
        System.out.println("3. Update Profil");
        System.out.println("4. Exit");
        System.out.print("Pilih Menu : ");
        select = in.nextInt();
        in.nextLine();
        System.out.println();

        if(select == 1){
            int i = 0;
            while(!dataMahasiswa[i][0].equals("#")){
                if(username.equals(dataMahasiswa[i][0])){
                    System.out.println("\n---------------- Nilai ----------------");
                    System.out.println("Al-Islam dan Kemuhammadiyahan III : " + nilaiMahasiswa[i][0]);
                    System.out.println("Algoritma Pemrograman\t\t\t  : " + nilaiMahasiswa[i][1]);
                    System.out.println("Grafika Komputer\t\t\t\t  : " + nilaiMahasiswa[i][2]);
                    System.out.println("Kewarganegaraan\t\t\t\t\t  : " + nilaiMahasiswa[i][3]);
                    System.out.println("Komunikasi Data\t\t\t\t\t  : " + nilaiMahasiswa[i][4]);
                    System.out.println("Metode Numerik\t\t\t\t\t  : " + nilaiMahasiswa[i][5]);
                    System.out.println("Statistika dan Probabilitas\t\t  : " + nilaiMahasiswa[i][6]);
                    System.out.println("Struktur Data\t\t\t\t\t  : " + nilaiMahasiswa[i][7]);
                    System.out.println("--------------------------------------");
                    break;
                }else {
                    i++;
                }
            }
        }

        else if(select == 2){
            int i = 0;
            while(!dataMahasiswa[i][0].equals("#")){
                if(username.equals(dataMahasiswa[i][0])){
                    System.out.println("----------------------------------------");
                    System.out.println("Nama\t\t: " + dataMahasiswa[i][0]);
                    System.out.println("NIM\t\t\t: " + dataMahasiswa[i][1]);
                    System.out.println("Usia\t\t: " + dataMahasiswa[i][2] + " Tahun");
                    System.out.println("Asal Daerah\t: " + dataMahasiswa[i][3]);
                    System.out.println("----------------------------------------");
                    break;
                }else {
                    i++;
                }
            }
        }

        else if(select == 3){
            int i = 0;
            while(!dataMahasiswa[i][0].equals("#")){
                if(username.equals(dataMahasiswa[i][0])){
                    System.out.print("Nama Baru        : ");
                    dataMahasiswa[i][0] = in.nextLine();
                    System.out.print("NIM Baru         : ");
                    dataMahasiswa[i][1] = in.nextLine();
                    System.out.print("Usia Baru        : ");
                    dataMahasiswa[i][2] = in.nextLine();
                    System.out.print("Asal Daerah Baru : ");
                    dataMahasiswa[i][3] = in.nextLine();
                    break;
                }else {
                    i++;
                }
            }
            sort();
            saveFile();
            System.out.println("\nData Sukses Di-Update !");
        }

        else if(select == 4){
            loop = false;
        }

        System.out.println();
    }

}
