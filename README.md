# Metin2-Auto-Fishing-Codes-Otomatik-Balik-Tutma-Program-
Metin2 Otomatik Balık Tutma

Java Programı ile yazılmıştır. Sadece 1 oyun hesabını destekler.
Program görüntü ekran kaydı alma mantığıyla yapılmıştır ve alınan görüntü işlenerek gerekli işlemler yapılmıştır.
@@@@@@@ Yani gerçek kişi balık tutuyormuş gibi program çalışır ve onun haricinde bilgisayarın başka bir program çalıştırması yada program açık iken sizin başka işlem yapmanız mümkün OLMAZ  @@@@@@@@@.

/********* Eğer yazılımcı değilseniz yada kodları kurmayı uğraşmadan çalıştırmak isteyenler

https://drive.google.com/file/d/1UMcy3OD60LuToW0TcMjW40KbWZM6KGCS/view?usp=drive_link

linkten zip dosyasını istediğiniz yere ayıklayıp "Mt2AutoFishingPublic.exe" programını "Yönetici olarak çalıştır" yaptıktan 3 5 saniye sonra program açılacak
Eğer yukarıdaki hazır programı kullanacaklar, "Requires Java Runtime Environment" gibi hata alıyor iseniz MUTLAKA JRE kurulumu yapmanız GEREKİR. İnternete "JRE Download" yazarak indirip gerekli kurulumu yapbalirisiniz oldukça KOLAYDIR internette bolca KURULUM VİDEOLARI mevcuttur. ***********/

<<<<<< Eğer program test ettikten sonra MEMNUN kalırsanız YILDIZ bırakmayı unutmayın >>>>>>>>>>>>>>>

PROGRAMI TEST ETMENİZ İÇİN BİLGİSAYARDA OLMASI GEREKEN İDEAL ÖZELLİKLER
- En az 2.80 Ghz işlemci(Altı olsada tutar ama balık tutma sayısı işlemci hızına göre çok değişkenlik gösterir..)
- Bilgisayarınızda aktif kullandığınız başka programlar yok ise sadece 2 Ghz Ram yeterli olacaktır.
- İşletim sistemi Windows 8, Windows 10 ve Windows 11 DESTEKLER...
  
PROGRAMIN YAPABİLDİKLERİ
- Yabbie, Altın Sudak, Kurbağa ve Palamut Balıkları Tutma ve Yakma
- Karakter öldüğünde Kanal Değiştirme
- Haritadan geçen dost ve düşman(lisan yüzüğü olması gerek) ile selam ve bazı kelimeleri algılayıp konuşma.
- Balıkçıdan kamp ataşi alıp yukarıdakileri balıkları yakma
- Solucan alıp slotlara dizme
- Oyundan atıldığı zaman heseba otomatik girme(gameforge simgesi sadece görev çubuğunda olmalı ve sadece balık tutan hesabın açık olması gerekir.)

!!! ŞAŞIRMAMANIZ GEREKEN SENARYOLAR !!!!

-Program Solucanların konulduğu slot yerlerinde solucan olup olmadığı kontrol eder. Eğer ilk yada son dört slot boşluğunda hiç solucan kalmamış ise karakter atar(Oyun kasma problemlerini önlemek için).Eğer programı başlatırken hiç dizili solucan yok ise karakter ard arda 8 kez (çünkü 4 slot sayfasına da bakıyor) ve ondan sonra BALIKÇIYA gidip oradan ilk kamp ataşini satın alır.Eğer yakılacak hiç balık yok ise tekrar balıkçı sayfasını açıp solucanları alıp dizer.

-Küçük haritadan birisini görür ise program gerçek kişi olduğu hissi vermek amaçlı otomatik olarak "kg reis ", "iyi oyunlar" gibi otomatik olarak chatten mesaj yazar.Bu sırada biraz balık tutma hızı bilerek yavaşlatılır... Eğer karşı taraf mesaj yazar ve onun mesajını anlayacak herhangi programda kayıtlı kelime yok ise " şu an balık tutmaya konstentreyim bu yüzden...." gibi muhabbetin fazla uzamaması için kestirme cevap verir :)(Yazılımcı olanlar Chatting sınıfında bu kodlamalar yapılmıştır sizde kafanıza göre cevapları değiştirip veya ona göre ekleyebilirsiniz.)

-Eğer haritadaki başka oyuncu belirlene süreden (ben 2 dakika diye yazmıştım) fazla duruyor ise sizin karakter "ben cıkıyorum iyi oyunlar" gibi mesaj yazıp CH değiştirir.

-Eğer balık tutar iken sizi NPC veya başka oyuncu öldürür ise otomatik olarak CH değişir.

^^^^^^^^ EĞER PROGRAM BUGTA KALIR İSE ^^^^^^^^^^^^

@@ Elbette ben şu an  programı geliştirmiyorum ama program % 90 civarlarında hatasız çalışıyor.Olduki herhangi yanlış davranış( Örneğin balık TUTMAMASI veya haritada bir yere sadece sık aralıklar ile tıklama yapması gibi ) durumlarında yapabiliyor iseniz ALT - TAB ile programa geçip hemen F4 tuşuna basarsanız programı KAPATABİLİRSİNİZ. Eğer YAPAMIYORSANIZ bilgisayarın DÜĞMESİNDEN kapatmanız gerekir.

@@ Program Start tuşuna basılarak Stop konumuna getirilebilir ama program yazılırken 3 ayrı Thread kullanılmıştır.Eğer bunlardan biri sonlanmaz ise Start butonu basılamaz hale gelir ve 7 saniyeden fazla bu halde TAKILI KALIR ise çarpı tuşundan programı kapatmanız gerekir.

ÖNEMLİ NOT!!!
 Program sadece Türkiye serveri için yazılmıştır. Test edecek kişi aşağıdaki  BAŞLAMADAN ÖNCE UYGULANMASI GEREKENLER!! kısmını yaptıktan sonra "START" tuşu ile çalıştırabilir. 
 "PREPARE FISHING" butonu buradaki yüklü kodda kaldırılmıştır. Ben test amaçlı kullanıyordum.
 "DEFINE SCREEN" butonu nesnelerin bilgisayar ekranındaki tıklanan yerin koordinat değerini dönmesini sağlar. Böylece resmini kaydettiğim resimlerden gerekli koordinatları kullanıp, istenilen resmi kaydediyorum.

 PROGRAMIN asıl amacı görüntü işleme mantığını öğrenmektir. 3 taraf yazılım kullanmak METİN2 tarafından izin VERİLMEMEKTEDİR ve kullanım sonucu oyundaki hesabınız ENGELLENİR!!!. 

 BAŞLAMADAN ÖNCE UYGULANMASI GEREKENLER!!

 @@@@@@@ Yazılımcılar için Hangi IDE kullanıyor iseniz başlatırken mutlaka YÖNETİCİ OLARAK ÇALIŞTIR yapmalısınız   @@@@@@@@@@@

 1.Oyun mutlaka 800x600 çözünürlük ve PENCERE modunda açılmalı.
 
 2.Mutlaka Balıkçılık Klavuzu Takılı Olmalı.
 
 3.Mutlaka Olta Takılı Olmalı.

 4.Mutlaka Envanterin 1. ve 2. sayfanın en sol üst kısmına ve karakterin üstünde zırh olmalı (Toplam 3 adet zırh)
 
 5.Metin2 ve Balık Tutma Programı Üst Üste olmamalı.
 
 6.Balıkçının olduğu köprüde karakter durmalı ve balıkçı karakterin arkasında kalmalı.
 
 7.Karatkerin bakış açısı denize doğru olması daha iyi olur.

![yabbie](https://github.com/mmtcoder/Metin2-Auto-Fishing-Codes-Otomatik-Bal-k-Tutma-Program-/assets/40866163/4f11e94c-a452-46d2-88f1-22b5b1dec850)

![altin](https://github.com/mmtcoder/Metin2-Auto-Fishing-Codes-Otomatik-Bal-k-Tutma-Program-/assets/40866163/bb878493-057c-4eca-a99a-8054b33f1e25)

 
