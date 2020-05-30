package com.bh183.hudaipi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_buku";
    private final static String TABLE_BUKU ="t_buku";
    private final static String KEY_ID_BUKU = "ID_Buku";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_PENULIS = "Penulis";
    private final static String KEY_PENERBIT = "Penerbit";
    private final static String KEY_GENRE = "Genre";
    private final static String KEY_TAHUN = "Tahun";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_SINOPSIS = "Sinopsis";
    private Context context;


    public DatabaseHandler(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BUKU = "CREATE TABLE " + TABLE_BUKU
                + "(" + KEY_ID_BUKU + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_PENULIS + " TEXT, "
                + KEY_PENERBIT + " TEXT, " + KEY_GENRE + " TEXT, "
                + KEY_TAHUN + " TEXT, " + KEY_GAMBAR + " TEXT, " + KEY_SINOPSIS + " TEXT);";

        db.execSQL(CREATE_TABLE_BUKU);
        inisialisasiBukuAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_BUKU;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahBuku(Buku dataBuku){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudul());
        cv.put(KEY_PENULIS, dataBuku.getPenulis());
        cv.put(KEY_PENERBIT, dataBuku.getPenerbit());
        cv.put(KEY_GENRE, dataBuku.getGenre());
        cv.put(KEY_TAHUN, dataBuku.getTahun());
        cv.put(KEY_GAMBAR, dataBuku.getGambar());
        cv.put(KEY_SINOPSIS, dataBuku.getSinopsis());


        db.insert(TABLE_BUKU, null, cv);
        db.close();
    }

    public void tambahBuku(Buku dataBuku, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudul());
        cv.put(KEY_PENULIS, dataBuku.getPenulis());
        cv.put(KEY_PENERBIT, dataBuku.getPenerbit());
        cv.put(KEY_GENRE, dataBuku.getGenre());
        cv.put(KEY_TAHUN, dataBuku.getTahun());
        cv.put(KEY_GAMBAR, dataBuku.getGambar());
        cv.put(KEY_SINOPSIS, dataBuku.getSinopsis());
        db.insert(TABLE_BUKU, null, cv);
    }

    public void editBuku(Buku dataBuku){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudul());
        cv.put(KEY_PENULIS, dataBuku.getPenulis());
        cv.put(KEY_PENERBIT, dataBuku.getPenerbit());
        cv.put(KEY_GENRE, dataBuku.getGenre());
        cv.put(KEY_TAHUN, dataBuku.getTahun());
        cv.put(KEY_GAMBAR, dataBuku.getGambar());
        cv.put(KEY_SINOPSIS, dataBuku.getSinopsis());

        db.update(TABLE_BUKU, cv, KEY_ID_BUKU + "=?", new String[]{String.valueOf(dataBuku.getIdBuku())});
        db.close();
    }

    public void hapusBuku (int idBuku){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BUKU, KEY_ID_BUKU + "=?", new String[]{String.valueOf(idBuku)});
        db.close();
    }

    public ArrayList<Buku> getAllBuku(){
        ArrayList<Buku> dataBuku = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_BUKU;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do {
                Buku tempBuku = new Buku(
                        csr.getInt(0),
                        csr.getString(1),
                        csr.getString(2),
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7)

                );

                dataBuku.add(tempBuku);
            } while (csr.moveToNext());
        }

        return dataBuku;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiBukuAwal(SQLiteDatabase db){
        int idBuku = 0;

        // Menambahkan data buku ke 1
        Buku buku1 = new Buku(
                idBuku,
                "Bumi Manusia",
                "Pramoedya Ananta",
                "Hasta Mitra",
                "Novel, Fiksi sejarah",
                "1980",
                storeImageFile(R.drawable.gambar1),
                "Novel ini berisikan sebuah cerita tentang perlawanan kaum pribumi melawan kolonialisme belanda. Cerita ini bermula saat pribumi bernama Minke pemuda pribumi putra seorang bupati yang berkesempatan menempuh pendidikan di H.B.S merupakan salah satu siswa yang pandai. Karakter minke adalah pribumi yang cerdas dan berani melawan penindasan terhadap dirinya. Ia diperolok temannya karena kulitnya berbeda dengan temannya keturunan belanda. Minke sangat mengagungkan eropa dan melupakan budayanya karena merasa eropa jauh lebih baik dalam segala hal.\n" +
                        "Minke diajak temannya berkunjung ke Wonokromo sebuah perusahaan dan perkebunan tebu milik Nyai Ontosoroh. Nyai Ontosoroh dipaksa menikah orang tuannya kepada orang belanda, dan dijadikan gundiknya. Awalnya Nyai Ontosoroh menolak dan benci kepada sang suami (Tuan Mellema). Namun seiring berjalannya waktu Tuan Mellema ternyata sangat saying pada Nyai Ontosoroh. Dari sang suami pula Nyai Ontosoroh belajar tentang perusahaan hingga benar-benar berpengalaman sampai seluruh perusahaan yang mengendalikan adalah Nyai Ontosoroh meskipun pada akhirnya . Pernikahan itu melahirkan Annelies yang cantik jelita keturunan jawa-belanda. Pertemuan itulah adalah pertemuan pertama yang membuat Annelies dan Minke jatuh cinta.  Suatu hari Minke diminta Nyai Ontosoroh untuk datang kembali ke Wonokromo karena sejak kepergiannya Annelies sering melamun dan pekerjaannya banyak yang salah. Minke menjadi tempat curahan hati Annelies ia bercerita tentag keluarganya bahwa karena suatu hal ayahnya yang dulu baik berubah karena suatu hal. Yaitu saat anak papanya datang dan memperolok papanya serta menghina mamanya dan menuntut haknya. Sejak itu sang papa jarang pulang dan Nyai Ontosoroh yang mengurus seluruh perusahaan dan perkebunan. Minke semakin dekat dengan keluarga itu. Bahkan Minke dan Annelies diperbolehkan tidur bersama satu ranjang.\n" +
                        "Minke dikejutkan saat suatu pagi ia dijemput agen polisi untuk dibawa ke kantor polisi, kemudian Minke naik dokar dan ternyata menuju ke gedung bupati kota B. ternyata saat itu adalah saat dimana ayah Minke diangkat menjadi bupati. Ayahhanda Minke sangat marah karena Minke tak pernah membalas surat dari sang ayah. Selesai berurusan di kota B Minke kembali ke Surabaya namun karena suatu hal demi menjaga kebaikan semuanya Minke tidak ke Wonokromo. Suatu hari Minke mendapat kabar bahwa Annelies sakit keras karena merindukan Minke. Nyai Ontosoroh memasrahkan Annelies pada Minke. Setelah kedatangan Minke Annelies sembuh. Berbagai masalah datang dalam kehidupan Minke dan Annelies. Minke melanjutkan pendidikannya hingga lulus sebagai lulusan terbaik H.B.S ia tak menyangka seorang pribumi berada diatas eropa. Dan di hari bahagia itu minke dan annelies mengumumkan pernikahannya. Pesta pernikahan besar-besaran digelar dengan tata cara islam.\n" +
                        "Enam bulan telah terlewati. Keluarga itu lagi-lagi dihantam badai. Annelies dan Nyai menghadap ke pengadilan putih yang memutuskan semua hak-hak kuasa kekayaan Tuan Mallema jatuh pada anak kandungnya. Hal itu membuat keluarga itu sangat terkejut. Juga surat yang menunjukkan bahwa Mauris Mellema menjadi wali bagi Annelies. Dan pengasuhnya di Nederland. Hal ini membuat Minke hampir pingsan. Sejak saat itupun kesehatan Annelies mulai terganggu. Nyai sudah menyepa advokat untuk membantu. Inilah perkara bangsa kulit putih yang menelan pribumi. Nyai dan Minke tak ingin menyerah dalam perkara ini. Mereka terus melawan. Pribumi harus mempertahankan hak-haknya tidak boleh ditindas eropa saja. Semua hal dilakukan Minke untuk mempertahankan Annelies dari menulis, berdemo hingga mengajak forum islam yang membela Minke. Hari terus berlalu sampailah pada saat-saat terakhir dimana Annelies akan pergi Annelies mempunyai permintaan terakhir kepada mamanya untuk mengasuh seorang adik perempuan mirip Annelies. Perempuan erop mulai menarik Annelies ia berjalan lambat-lambat menuruni tangga dalam tuntunan orang eropa. Badannya Nampak sangat rapuh dan lemah. Anneliespun pergi menuju dimana ratu Wilhelnima berkuasa."
        );

        tambahBuku(buku1, db);
        idBuku++;

        // Menambahkan data buku ke 2
        Buku buku2 = new Buku(
                idBuku,
                "Sang Pemimpi",
                "PAndrea Hirata",
               "PBentang Pustaka",
                "Novel, Fiksi",
                "2006",
                storeImageFile(R.drawable.gambar2),
                "Novel Sang Pemimpi menceritakan tentang sebuah kehidupan tiga orang anak Melayu Belitong yaitu Ikal, Arai, dan Jimbron yang penuh dengan tantangan, pengorbanan dan lika-liku kehidupan yang memesona sehingga kita akan percaya akan adanya tenaga cinta, percaya pada kekuatan mimpi dan kekuasaan Allah. Ikal, Arai, dan Jimbron berjuang demi menuntut ilmu di SMA Negeri Bukan Main yang jauh dari kampungnya. Mereka tinggal di salah satu los di pasar kumuh Magai Pulau Belitong bekerja sebagai kuli ngambat untuk tetap hidup sambil belajar.\n" +
                        "\n" +
                        "Ada Pak Balia yang baik dan bijaksana, beliau seorang Kepala Sekolah sekaligus mengajar kesusastraan di SMA Negeri Bukan Main, dalam novel ini juga ada Pak Mustar yang sangat antagonis dan ditakuti siswa, beliau berubah menjadi galak karena anak lelaki kesayangannya tidak diterima di SMA yang dirintisnya ini. Sebab NEM anaknya ini kurang 0,25 dari batas minimal. Bayangkan 0,25 syaratnya 42, NEM anaknya hanya 41,75. Ikal, Arai, dan Jimbron pernah dihukum oleh Pak Mustar karena telah menonton film di bioskop dan peraturan ini larangan bagi siswa SMA Negeri Bukan Main. Pada apel Senin pagi mereka barisnya dipisahkan, dan mendapat hukuman berakting di lapangan sekolah serta membersihkan WC.\n" +
                        "\n" +
                        "Ikal dan Arai bertalian darah. Nenek Arai adalah adik kandung kakek Ikal dari pihak ibu,ketika kelas 1 SD ibu Arai wafat dan ayahmya juga wafat ketika Arai kelas 3 sehingga di kampung Melayu disebut Simpai Keramat. Sedangkan Jimbron bicaranya gagap karena dulu bersama ayahnya bepergian naik sepeda tiba-tiba ayahnya kena serangan jantung dan Jimbron pontang-panting membawa ayahnya panik. Ia sangat antusias sekali dengan kuda, segala macam kuda ia tahu. Ayah Ikal bekerja di PN Timah Belitong, ayahnya pendiam tapi kasih sayangnya sangat besar, dia bersepeda ke Magai 30 kilometer hanya untuk mengambil rapot anaknya di SMA Negeri Bukan Main. Dan ibu Ikal menyiapkan baju safari ayah dengan menyalakan setrika arang dan gesit memercikan air pandan dan bunga kenanga yang telah direndam semalam.\n" +
                        "\n" +
                        "Ketika belajar di lapangan sekolah Pak Mustar berkata : “Jelajahi kemegahan Eropa sampai ke Afrika yang eksotis. Temukan berliannya budaya sampai ke Prancis. Langkahkan kakimu di atas altar suci almamater terhebat tiada tara Sorbonne. Ikuti jejak-jejak Sartre, Louis Pasteur, Montesquieu, Voltaire. Disanalah orang belajar science, sastar, dan seni hingga mengubah peradaban”. Ikal dan Arai tak berkedip ketika Pak Balia memperlihatkan gambar yang tampak seorang pelukis dibelakang kanvas berdiri menjulang Menara Eiffel yang menunduk memerintahkan Sungai Seine agar membelah diri menjadi dua tepat dikaki-kakinya.\n" +
                        "\n" +
                        "Saat itulah mereka mengkristalkan harapan agung dengan statement yang sangat ambisius : Cita-cita kami adalah kami ingin sekolah ke Prancis! Ingin menginjakan kaki di altar suci almamater Sorbonne, ingin menjelajah Eropa sampai ke Afrika. Dengan perjuangan hidup mesti serba terbatas dan banyak rintangan Ikal dan Arai akhirnya diterima kuliah di Universite de Paris, Sorbonne, Prancis. Sedangkan Jimbron tetap di Belitong mengurusi kuda milik capo.\n" +
                        "\n" +
                        "Novel Sang Pemimpi menceritakan tentang sebuah kehidupan tiga orang anak Melayu Belitong yaitu Ikal, Arai, dan Jimbron yang penuh dengan tantangan, pengorbanan dan lika-liku kehidupan yang memesona sehingga kita akan percaya akan adanya tenaga cinta, percaya pada kekuatan mimpi dan kekuasaan Allah. Ikal, Arai, dan Jimbron berjuang demi menuntut ilmu di SMA Negeri Bukan Main yang jauh dari kampungnya. Mereka tinggal di salah satu los di pasar kumuh Magai Pulau Belitong bekerja sebagai kuli\n" +
                        "ngambat untuk tetap hidup sambil belajar. Ada Pak Balia yang baik dan bijaksana, beliau seorang Kepala Sekolah sekaligus mengajar kesusastraan di SMA Negeri Bukan Main, dalam novel ini juga ada Pak Mustar yang sangat antagonis dan ditakuti siswa, beliau berubah menjadi galak karena anak lelaki kesayangannya tidak diterima di SMA yang dirintisnya ini. Sebab NEM anaknya ini kurang 0,25 dari batas minimal.\n" +
                        "\n" +
                        "Bayangkan 0,25 syaratnya 42, NEM anaknya hanya 41,75. Ikal, Arai, dan Jimbron pernah dihukum oleh Pak Mustar karena telah menonton film di bioskop dan peraturan ini larangan bagi siswa SMA Negeri Bukan Main. Pada apel Senin pagi mereka barisnya dipisahkan, dan mendapat hukuman berakting di lapangan sekolah serta membersihkan WC. Ikal dan Arai bertalian darah. Nenek Arai adalah adik kandung kakek Ikal dari pihak ibu,ketika kelas 1 SD ibu Arai wafat dan ayahmya juga wafat ketika Arai kelas 3 sehingga di kampung Melayu disebut Simpai Keramat. Sedangkan Jimbron bicaranya gagap karena dulu bersama ayahnya.\n" +
                        "\n" +
                        "Kisah dalam novel ini dimulai dengan kehidupan tokoh ikal di Belitong pada saat ia masih SMA. Ia bersama saudara jauhnya yakni Ikal menjalani masa SMA yang menyenangkan meski berat sebab tuntutan ekonomi membuat mereka dewasa sebelum waktunya. Untuk tetap besekolah dan hidup, keduanya bekerja sebagai kuli di sebuah pelabuhan ikan. Waktu kerja mereka dini hari sehingga waktu sekolah tidak terganggu. Kegigihan mereka pada akhirnya terbayar saat mereka dewasa kelak. Ikal sendiri berhasil mendapatkan gelar sarjana ekonomi dari Universitas Indonesia, sementara Arai yang pada akhirnya kuliah di Kalimantan, menjadi seorang ahli biologi.\n" +
                        "\n" +
                        "Selain Ikal dan Arai, ada tokoh sentral lain dalam novel Sang Pemimpi ini. Ia adalah Jimbron. Ia sendiri adalah anak yatim piatu yang diceritakan diasuh oleh seseorang bernama Geovanny. Ia berwajah bayi dengan tubuh gembur. Pemikirannya lurus, cenderung naïf dan polos. Jimbron sangat menyukai kuda dan tahu seluk beluk hewan tangkas tersebut. Jimbron menjadi perekat hubungan Ikal dan Arai, oleh sebab keluguannya, ia mudah disayangi dan mendapat simpati. Persahabatan mereka juga tentang bagaimana melindungi Jimbron. Namun, selepas SMA, ketiga sahabat ini berpisah. Mereka berbeda rute dan dipisahkan kota.\n" +
                        "\n" +
                        "Ada banyak tokoh pembantu lainnya dalam cerita ini antara lain Pak Mustar, Pak Drs. Julian Ichsan Balia, Nurmalala, Lakshmi, Taikong Hamim, Bang Zaitun dan masih banyak lagi lainnya. Kesemua tokoh ini mewarnai dinamika perjuangan Arai juga Ikal meraih mimpi. Novel ini menarik dengan bahasa yang tentu apik khas Andrea Hirata. Meski memang tak sefeonomenas Laskar Pelangi, namun Sang Pemimpi ini seperti sebuah “penuntasan” dari apa yang dikosongkan Laskar Pelangi. Sama seperti cerita tetralogi lainnya, saat Anda membaca buku pertama, maka seyogyanya Anda juga menuntaskkan novel lanjutannya..\n"
        );

        tambahBuku(buku2, db);
        idBuku++;

        // Menambahkan data buku ke 3
        Buku buku3 = new Buku(
                idBuku,
                "Rumah Kaca",
                "Pramoedya Ananta",
                "Lentera Dipantara",
                "Fiksi sejarah",
                "1988",
                storeImageFile(R.drawable.gambar3),
                "Rumah Kaca (1988) adalah buku terakhir dari tetralogi Buru. Pada bagian ini diambil sudut pandang yang sama sekali tidak terduga dari tiga cerita sebelumnya. Jika kita masih ingat kisah menegangkan di akhir cerita Jejak Langkah; setelah terjadi peristiwa penembakan Robert Suurhof gembong preman De Zweep yang kerap mengganggu aktivitas politik Minke yang ternyata dilakukan atas rencana istrinya sendiri--Prinses van Kasurita dari Maluku yang digambarkan sebagai perempuan yang sangat pemberani--tanpa sepengetahuan Minke itu taklain hanyalah sebuah rencana di balik rencana seorang petinggi polisi Belanda dari Ambon, Pangemanann dengan dua n, untuk menjebak Minke.\n" +
                        "\n" +
                        "Rumah Kaca ini dikisahkan ditulis sendiri oleh Pangemanann sebagai sebuah proyek (studi) pribadi tentang perkembangan pemberontakan pribumi. Memang Pangemanann-lah (seorang pribumi, di dalam cerita) yang ditugasi pemerintah konolonial untuk mulai melacak akar-akar pemikiran pemberontakan pribumi yang dalam pengamatannya bermuara pada aktivitas Minke--dengan segala kegiatannya; penerbitan koran, partai, majalah, lembaga bantuan hukum, dsb. Maka, dibuatlah rencana jahat untuk membungkam semua kegiatannya dengan menjebak Minke ke dalam sebuah konspirasi busuk. Minke harus berbuat salah. Lalu dia harus dihukum.\n" +
                        "\n" +
                        "Tapi, ternyata sangat tidak mudah, Minke sebagai seorang cendekia pribumi, sadar benar untuk mempelajari hukum Belanda. Maka, rencana busuk Pangemann ditujukan kepada istrinya. Pangemanann dengan sangat telaten mempelajari sifat dan informasi tentang Prinses van Kasurita, dengan kesimpulan; Prinses akan cukup berani untuk membunuh seseorang demi keselamatan suaminya. Rencana itu mulai dihembuskan lewat desas-desus bahwa Robert Suurhof akan segera menghabisi Minke.\n" +
                        "\n" +
                        "Dan, terjadilah apa yang direncanakan Prinses untuk menembak gembong preman itu di tengah keramaian pasar, agar mudah menghilangkan jejak di tengah keributan yang akan terjadi, juga rencana ini dibantu teman-teman baik Minke, tanpa sepengetahuan Minke. Padahal itulah rencana besar Pangemanann untuk menjebak Minke, sebab semua barang bukti akan menjurus pada satu-satunya senjata api yang resmi dimiliki Minke. Penembakan pun terjadi, sedikit di luar perhitungan, peristiwa itu terjadi ketika Pangemanann (yang menyamar sebagai seorang penulis cerita Si Pitung) justru sedang bersantap dengan Minke. (Ini bisa mengurangi tuduhan langsung bahwa Minke-lah yang menembak Suurhof, sebab Pangemanann-lah saksi yang menyertainya ketika peristiwa itu terjadi).\n" +
                        "\n" +
                        "Namun, tak aral tanggung, rencana tuduhan pembunuhan pun langsung dialamat kepada Minke. Ia dijemput oleh satu kompi (ini sangat berlebihan) polisi Belanda yang memang sudah dipersiapkan sebelumnya. Kali ini yang menjemput Minke adalah Pangemanann sendiri (sebagai seorang petinggi polisi Belanda). Cerita berakhir dan mengambang (dalam Jejak Langkah) tentang mau dibawa ke mana Minke yang ditangkap tanpa kesempatan membela diri. Ternyata Minke (dalam Rumah Kaca), atas titah Gubernur Jendral Hindia-Belanda, diangsingkan ke pulau terpencil di Maluku Utara dan semua kegiatannya dibekukan oleh pemerintah kolonial--termasuk semua aset dan hasil jerih payahnya (tabungan uang) tanpa sepengetahuannya.\n" +
                        "\n" +
                        "Gubernur Jendral Hindia-Belanda pun berganti. Minke telah menjalani 5 tahun hukuman pembuangan. Siksaan yang takterkira bagi seorang aktivis yang giat, mendekam dengan gerak yang dibatasi pemerintah kolonial. Tanpa korespondensi, tanpa komunikasi. Setelah 5 tahun di pengasingan, akhirnya Minke dikirim pulang. Namun, pulang hanya menyisakan kekecewaan yang semakin menyakitkan dari apa yang telah ditinggalkannya. Minke menelan kekecewaan menjadi sakit-sakitan di tengah kesengsaraan. Dan, jatuh meninggal karena sakit yang taktertolong (dokter di bawah ancaman tangan-tangan kolonial untuk tidak mengobati pasiennya).\n"
        );

        tambahBuku(buku3, db);
        idBuku++;

        // Menambahkan data buku ke 4
        Buku buku4 = new Buku(
                idBuku,
                "Perahu Kertas",
                "Dee Lestari",
                "Bentang Pustaka",
                "Fiksi",
                "2003",
                storeImageFile(R.drawable.gambar4),
                "Novel Perahu Kertas dimulai dengan kisah seorang anak muda bernama Keenan. Ia seorang remaja yang baru saja menyelesaikan sekolah menengah atas-nya di Belanda, tepatnya di Amsterdam. Keenan menetap di Negara tersebut selama hampir 6 tahun lamanya, bersama sang nenek. Keenan terlahir dengan cita-cita menjadi pelukis. Namun, ia dipaksa untuk kembali ke Indonesia oleh sang Ayah. Keluarganya tidak mendukung Keenan menjadi seorang pelukis. Ia pada akhirnya memulai perkuliahan di salah satu Universitas di Bandung. Ia mengalah dan memutuskan untuk belajar di Fakultas Ekonomi.\n" +
                        "\n" +
                        "Tokoh sentral lainnya adalah wanita bertubuh mungil bernama Kugy. Ia digambarkan dengan kepribadian yang riang dan ceria. Berbeda dengan Keenan yang cenderung dingin dan kaku. Kugy juga merupakan sosok yang eksentrik pun nyentrik. Ia akan sangat mudah dikenali jika ada di dalam kerumunan. Kugy menggilai dongeng dan kisah klasik. Sedari kecil ia bercita-cita menjadi seorang penulis dongeng. Ia memiliki sejumlah koleksi buku dongeng, ingin penjadi seorang perancang dongen pun juru dongeng. Namun di tengah impiannya yang menggebu, kenyataan memaksanya sadar bahwa penulis dongen bukan profesi yang banyak menghasilkan materi. Kugy dipaksa untuk menyimpan mimpinya demi sebuah rasionalitas pun realisme. Meski demikian, tokoh Kugy ini tidak patah arang. Ia mencintai dunia tulis-menulis. Hal ini yang membuat ia melanjutkan pendidikannya di Fakultas Sastra di salah satu Universitas di Bandung. Tempat kuliah yang sama dengan tokoh lainnya, Keenan.\n" +
                        "\n" +
                        "Pertemuan antara kedua tokoh ini tak terlepas dari tokoh lain yakni Noni dan Eko. Noni tokoh pendukung cerita yang merupakan sahabat dekat Kugy. Sementara itu, Eko adalah sepupu Keenan. Pertemuan pertama Kugy dan Keenan adalah momen dimana Eko dan Noni menjemput Keenan yang baru tiba di Indonesia.\n" +
                        "\n" +
                        "Seiring berjalannya waktu, Kugy pun Keenan menjalin persahabatan bersama Eko dan Noni. Diam-diam, mereka saling mengagumi. Kugy yang senang bercerita lewat dongeng merasa takjub bertemu dengan Keenan, seseorang yang mampu bercerita lewat gambar. Mereka diam-diam jatuh cinta dalam diam. Namun, kondisi menuntut mereka untuk terus diam dan menebak. “Diam”-nya mereka terhadap perasaan masing-masing semakin menjadi dikarenakan Kugy telah memiliki pacar bernama Ojos atau Joshua. Sementara itu, Keenan yang belum memiliki pasangan, hendak dijodohkan dengan tokoh bernama Wanda. Wanda sendiri adalah seorang Kurator. Hal ini yang membuat Eko juga Noni bersemangat mendekatkannya dengan Keenan yang jago melukis.\n" +
                        "\n" +
                        "Persahabatan Kugy, Keenan, Eko dan Noni berjalan apa adanya. Namun lambat laun mereka renggang. Kugy sibuk dengan muridnya di sekolah darurat. Ia menjadi salah satu guru relawan. Ia mengajar dengan cara mendongeng. Anak-anak yang semula usil pada Kugy, berbalik suka berkat dongeng petualangan berjudul “Jenderal Pilik dan Pasukan Alit”. Dongeng tersebut dituliskan Kugy dalam sebuah buku. Di waktu mendatang, buku dongeng tersebut ia berikan pada Keenan.\n" +
                        "\n" +
                        "Lain lagi dengan Keenan, ia juga sibuk dengan kehidupannya termasuk kedekatannya dengan Wanda. Pada mulanya, hubungan mereka baik-baik saja. Namun, beberapa waktu hubungan tersebut menjadi pelik dan menghentak Keenan. Ia menyadari bahwa apa yang ia berusaha bangun, hancur dalam hitungan waktu semalam. Ia sedih, remuk dan kecewa. Keenan pun memutuskan untuk meninggalkan Kota Bandung menuju Kota Bali. Di Pulau Dewata tersebut, Keenan tinggal dengan Pak Wayan. Sahabat ibunya.\n" +
                        "\n" +
                        "Sebelum pergi, Kugy memberi Keenan buku dongen “Jenderal Pilik dan Pasukan Alit”. Keenan membawanya ke Bali. Di tempat Pak Wayan, perlahan Keenan membangun hidup dan mimpinya kembali. Ia hidup bersama banyak seniman dan menjadikan naluri seninya dalam melukis semakin terasah. Di Bali, Keenan mengagumi Luhde Laksmi, keponakan Pak Wayan. Pada akhirnya, Setelah beberapa waktu, Keenan menjadi salah satu pelukis yang karyanya diburu. Ia menciptakan serial lukisan yang digemari kolektor. Kisah tersebut adalah dongeng yang sebelumnya Kugy berikan.\n" +
                        "\n" +
                        "Sementara itu, selepas kuliah Kugy kembali ke Jakarta dan menjadi seorang Copywriter. Ia kemudian menjalin hubungan dengan atasannya yang juga merupakan karib kakaknya. Ia dan Remi menjalin hubungan meski diam-diam Kugy masih sering mengenang Keenan. Sampai suatu waktu, Kugy kembali bertemu dengan Keenan yang terpaksa meninggalkan Bali karena ayahnya terkena serangan stroke. Keenan harus melanjutkan perusahaan ayahnya. Pertemuan Kugy dan Keenan di kondisi yang berbeda ini membuat mereka tak bisa lagi menahan perasaan masing-masing. Konflik dimulai dari sini”\n"
        );

        tambahBuku(buku4, db);
        idBuku++;


    }
}
