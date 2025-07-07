# Twitter API Backend Projesi

Bu proje, Spring Boot kullanarak geliştirilmiş, Twitter uygulamasının temel özelliklerini (tweet atma, yorum yapma, beğeni) taklit eden bir backend API'sidir. Projenin amacı, katmanlı mimari, veritabanı yönetimi ve RESTful API tasarımı gibi modern backend geliştirme pratiklerini uygulamaktır.

## Proje Amacı

Bu projenin temel amacı, Spring Boot çerçevesini kullanarak öğrendiğimiz backend geliştirme prensiplerini pratik etmek ve bir sosyal medya uygulamasının (Twitter benzeri) arka planındaki veri akışını, iş mantığını ve API etkileşimlerini nasıl tasarlayacağımızı göstermektir. Özellikle kullanıcıların tweet atabildiği, mevcut tweetlere yorum yapabildiği ve beğeni (like) atabildiği temel fonksiyonelliklere odaklanılmıştır.

## Kullanılan Teknolojiler

* **Java 17:** Geliştirme dili.
* **Spring Boot 3.x:** Backend çerçevesi.
* **Spring Data JPA:** Veritabanı etkileşimleri için.
* **Hibernate:** JPA implementasyonu (ORM aracı).
* **PostgreSQL:** İlişkisel veritabanı.
* **Lombok:** Boilerplate kodunu azaltmak için (getter/setter, constructor vb.).
* **Maven:** Bağımlılık yönetimi ve proje derleme aracı.

## Mimari Yapı

Proje, sorumlulukların ayrıştırılması (Separation of Concerns) prensibine uygun olarak **katmanlı bir mimari** ile tasarlanmıştır:

1.  **Entity Katmanı:**
    * Veritabanındaki tablolara karşılık gelen Java sınıflarını (örn. `User`, `Tweet`, `Comment`, `Like`) içerir.
    * `@Entity`, `@Table`, `@Id`, `@GeneratedValue` gibi JPA anotasyonları ile veritabanı eşleştirmelerini tanımlar.
    * İki yönlü ilişkilerde sonsuz döngü sorununu çözmek için `@JsonManagedReference` ve `@JsonBackReference` anotasyonları kullanılmıştır.

2.  **Repository Katmanı:**
    * Veritabanı ile doğrudan iletişim kuran arayüzleri (örn. `UserRepository`, `TweetRepository`) içerir.
    * Spring Data JPA'nın `JpaRepository` arayüzünden miras alarak temel CRUD (Create, Read, Update, Delete) operasyonlarını otomatik olarak sağlar.
    * `findByUserIdAndTweetId` gibi özel sorgu metotları, metot isimlendirme kuralları veya `@Query` anotasyonları ile tanımlanmıştır.

3.  **Service Katmanı:**
    * Uygulamanın **iş mantığını (business logic)** barındırır.
    * Controller'dan gelen istekleri işler, gerekli validasyonları yapar ve Repository katmanını kullanarak veritabanı operasyonlarını gerçekleştirir.
    * Örnek: `TweetService`, bir tweet oluşturmadan önce kullanıcının varlığını kontrol eder. `LikeService`, kullanıcının aynı tweete tekrar like atmasını engeller.

4.  **Controller Katmanı:**
    * Gelen HTTP isteklerini (GET, POST, PUT, DELETE) karşılamaktan ve istemciye JSON formatında yanıt dönmekten sorumludur.
    * Service katmanındaki metotları çağırır ve iş mantığını Controller seviyesinde tutmaz.
    * `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping` gibi Spring Web anotasyonları ile endpoint'leri tanımlar.

## Kurulum ve Çalıştırma

1.  **PostgreSQL Kurulumu:** Yerel makinenizde bir PostgreSQL sunucusu çalışıyor olmalıdır.
2.  **Veritabanı Oluşturma:** PostgreSQL'de `twitter_db` adında yeni bir veritabanı oluşturun.
3.  **Veritabanı Ayarları:** `src/main/resources/application.properties` (veya `application.yml`) dosyasında veritabanı bağlantı bilgilerini güncelleyin:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/twitter_db
    spring.datasource.username=YOUR_POSTGRES_USERNAME
    spring.datasource.password=YOUR_POSTGRES_PASSWORD
    spring.jpa.hibernate.ddl-auto=update # Uygulama başlatıldığında tabloları otomatik oluşturur/günceller.
    spring.jpa.show-sql=true
    ```
4.  **Projeyi Derleme:** Proje dizininde aşağıdaki Maven komutunu çalıştırın:
    ```bash
    mvn clean install
    ```
5.  **Uygulamayı Çalıştırma:**
    ```bash
    mvn spring-boot:run
    ```
    Uygulama varsayılan olarak `http://localhost:8080` adresinde başlayacaktır.

## Mevcut Endpoint'ler (Postman ile Test Edilebilir)

### Kullanıcı (User) İşlemleri
* **Kullanıcı Oluşturma:** `POST http://localhost:8080/user`
    * **Body (JSON):** `{"email": "test@example.com", "password": "password123", "firstName": "Test", "lastName": "User"}`
* **Tüm Kullanıcıları Getirme:** `GET http://localhost:8080/user`
* **ID'ye Göre Kullanıcı Getirme:** `GET http://localhost:8080/user/{id}`
* **Kullanıcı Güncelleme:** `PUT http://localhost:8080/user/{id}`
    * **Body (JSON):** `{"email": "updated@example.com", "password": "newpassword", "firstName": "Updated", "lastName": "User"}`
* **Kullanıcı Silme:** `DELETE http://localhost:8080/user/{id}`

### Tweet İşlemleri
* **Tweet Oluşturma:** `POST http://localhost:8080/tweet/user/{userId}`
    * **Body (JSON):** `{"content": "Bu benim ilk tweetim!"}`
* **Tüm Tweetleri Getirme:** `GET http://localhost:8080/tweet`
* **ID'ye Göre Tweet Getirme:** `GET http://localhost:8080/tweet/{id}`
* **Bir Kullanıcının Tweetlerini Getirme:** `GET http://localhost:8080/tweet/byUser/{userId}`
* **Tweet Güncelleme:** `PUT http://localhost:8080/tweet/{tweetId}/user/{userId}`
    * **Body (JSON):** `{"content": "Güncellenmiş tweet içeriği."}`
    * **Not:** Sadece tweet sahibi güncelleyebilir.
* **Tweet Silme:** `DELETE http://localhost:8080/tweet/{tweetId}/user/{userId}`
    * **Not:** Sadece tweet sahibi silebilir.

### Yorum (Comment) İşlemleri
* **Yorum Oluşturma:** `POST http://localhost:8080/comment/user/{userId}/tweet/{tweetId}`
    * **Body (JSON):** `{"text": "Bu tweet harika olmuş!"}`
* **Yorum Güncelleme:** `PUT http://localhost:8080/comment/{commentId}/user/{userId}`
    * **Body (JSON):** `{"text": "Güncellenmiş yorum içeriği."}`
    * **Not:** Sadece yorum sahibi veya tweet sahibi güncelleyebilir.
* **Yorum Silme:** `DELETE http://localhost:8080/comment/{commentId}/user/{userId}`
    * **Not:** Sadece yorum sahibi veya tweet sahibi silebilir.

### Beğeni (Like) İşlemleri
* **Beğeni Ekleme:** `POST http://localhost:8080/like/user/{userId}/tweet/{tweetId}`
    * **Not:** Aynı kullanıcı aynı tweete birden fazla beğeni atamaz.
* **Beğeni Silme (Unlike):** `DELETE http://localhost:8080/like/{likeId}/user/{userId}`
    * **Not:** Sadece beğeniyi atan kullanıcı silebilir.

## Gelecek Planları (Mimari İyileştirmeler)

Projemizi daha da sağlam ve ölçeklenebilir hale getirmek için aşağıdaki mimari iyileştirmeleri yapmayı planlıyoruz:

* **Entity Validasyonları:** Veritabanına kaydedilmeden önce entity alanlarına (örn. boş olamaz, belirli bir formatta olmalı) kısıtlamalar ekleyerek veri bütünlüğünü sağlamak.
* **Global Exception Handling:** Uygulama genelindeki hataları merkezi olarak yönetmek ve istemciye daha anlamlı HTTP durum kodları ve JSON hata mesajları döndürmek.
* **Spring Security Entegrasyonu:** Kullanıcı kimlik doğrulama (Authentication) ve yetkilendirme (Authorization) süreçlerini yönetmek için JWT (JSON Web Token) tabanlı bir güvenlik katmanı eklemek. Bu sayede `/register` ve `/login` gibi endpoint'ler oluşturulacak ve belirli API yollarına erişim kısıtlamaları getirilecektir.
* **Unit Testler:** Yazılan fonksiyonların belirli bir yüzdesi için birim testleri yazarak kod kalitesini ve güvenilirliğini artırmak.
