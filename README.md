# Fitness App - Spring Boot Backend

## Overview

This project implements a RESTful API backend for a fitness tracking application using the Spring Boot framework. It facilitates the management of user profiles, exercise definitions, and detailed workout session logging, including specific metrics for exercises performed within each session. The application demonstrates core backend development principles including layered architecture, relational data modeling with JPA, REST API design, and standard development practices.

---

## Genel Bakış

Bu proje, Spring Boot çatısı kullanılarak geliştirilmiş bir fitness takip uygulaması için RESTful API backend'ini implemente eder. Kullanıcı profillerinin, egzersiz tanımlarının ve her bir seanstaki egzersizlere ait spesifik metrikleri içeren detaylı antrenman kayıtlarının yönetilmesini sağlar. Uygulama, katmanlı mimari, JPA ile ilişkisel veri modelleme, REST API tasarımı ve standart geliştirme pratikleri dahil olmak üzere temel backend geliştirme prensiplerini sergilemektedir.

## Core Features / Temel Özellikler

*   **User Management / Kullanıcı Yönetimi:** Provides endpoints for user registration, retrieval (by ID or username), updates, and deletion. / Kullanıcı kaydı, getirme (ID veya kullanıcı adına göre), güncelleme ve silme işlemleri için endpoint'ler sunar.
*   **Exercise Definition / Egzersiz Tanımlama:** Allows defining standard exercises with descriptions and target muscle groups via CRUD operations. / Standart egzersizlerin açıklamaları ve hedef kas grupları ile birlikte CRUD operasyonları aracılığıyla tanımlanmasına olanak tanır.
*   **Workout Session Logging / Antrenman Seansı Kaydı:** Enables creating, retrieving (per user with pagination/sorting), updating (metadata like date/notes), and deleting workout sessions associated with users. / Kullanıcılarla ilişkili antrenman seanslarının oluşturulmasını, getirilmesini (kullanıcı bazında, sayfalama/sıralama ile), güncellenmesini (tarih/not gibi üst veriler) ve silinmesini sağlar.
*   **Detailed Exercise Tracking / Detaylı Egzersiz Takibi:** Facilitates logging specific exercises within a workout session, capturing details such as sets, repetitions, weight, and/or duration via a Many-to-Many relationship implemented with a dedicated join entity (`WorkoutLogEntry`). / Bir antrenman seansı içindeki belirli egzersizlerin set, tekrar, ağırlık ve/veya süre gibi detaylarını, adanmış bir birleştirme varlığı (`WorkoutLogEntry`) ile implemente edilen Many-to-Many ilişkisi üzerinden kaydetmeyi kolaylaştırır.
*   **Data Validation / Veri Doğrulama:** Implements input validation using Jakarta Bean Validation constraints. / Gelen istek verilerinin geçerliliğini Jakarta Bean Validation kısıtlamaları kullanarak uygular.
*   **Centralized Error Handling / Merkezi Hata Yönetimi:** Utilizes `@ControllerAdvice` for consistent handling of exceptions (e.g., `ResourceNotFoundException`, validation errors) and returns standardized error responses. / İstisnaların (örneğin, `ResourceNotFoundException`, validasyon hataları) tutarlı bir şekilde ele alınması ve standartlaştırılmış hata yanıtlarının döndürülmesi için `@ControllerAdvice` kullanır.
*   **DTO Pattern / DTO Deseni:** Leverages Data Transfer Objects (DTOs) for API responses to ensure a stable API contract decoupled from the internal entity structure and to mitigate issues related to lazy-loading serialization. / API katmanını iç entity yapısından ayırmak ve lazy-loading serileştirme ile ilgili sorunları azaltmak amacıyla API yanıtları için Veri Transfer Nesnelerini (DTO) kullanır.
*   **API Documentation / API Dokümantasyonu:** Provides interactive API documentation via Swagger UI, generated using Springdoc OpenAPI. / Springdoc OpenAPI kullanılarak oluşturulan Swagger UI aracılığıyla interaktif API dokümantasyonu sağlar.

## Technical Stack / Teknik Yığın

*   **Backend Framework:** Spring Boot 3.2.7
*   **Language:** Java 20 / 17
*   **Data Persistence:** Spring Data JPA, Hibernate 6.4.x
*   **Database:** PostgreSQL (Containerized with Docker for development) / PostgreSQL (Geliştirme için Docker ile konteynerize edilmiş)
*   **API:** RESTful Web Services (Spring Web MVC)
*   **Security:** Spring Security (Basic configuration) / (Temel yapılandırma)
*   **Validation:** Jakarta Bean Validation
*   **API Documentation:** Springdoc OpenAPI (Swagger UI)
*   **Build Tool:** Apache Maven
*   **Utilities:** Lombok, Docker

## Architectural Highlights / Mimari Önemli Noktalar

*   Layered Architecture (Controller-Service-Repository) / Katmanlı Mimari
*   Dependency Injection / Bağımlılık Enjeksiyonu
*   DTO Pattern for API responses / API yanıtları için DTO Deseni
*   Centralized Exception Handling / Merkezi İstisna Yönetimi
*   Many-to-Many relationship modeling via Join Entity / Birleştirme Varlığı ile Many-to-Many ilişki modellemesi
*   Pagination and Sorting support / Sayfalama ve Sıralama desteği

## 🚀 Getting Started / Başlarken

### Prerequisites / Gereksinimler

*   Java JDK 17 or 20 (Verify version in `pom.xml`) / Java JDK 17 veya 20 (`pom.xml`'deki sürümü doğrulayın)
*   Apache Maven
*   Docker Desktop or Docker Engine installed and running / Docker Desktop veya Docker Engine kurulu ve çalışır durumda.
*   Git (Optional) / Git (İsteğe bağlı)

### Setup and Execution / Kurulum ve Çalıştırma

1.  **Clone the Repository / Depoyu Klonlayın:**
    ```bash
    git clone https://github.com/YOUR_USERNAME/fitnessapp.git # Update with your repository URL / URL'yi kendi deponuzla güncelleyin
    cd fitnessapp
    ```

2.  **Start PostgreSQL Container / PostgreSQL Konteynerini Başlatın:**
    Execute the following command in your terminal, replacing `YOUR_SECURE_PASSWORD` with your desired password. / Terminalinizde aşağıdaki komutu çalıştırın, `SENIN_GUVENLI_SIFREN` kısmını istediğiniz bir şifre ile değiştirin.
    ```bash
    docker run --name fitnessapp-postgres -e POSTGRES_DB=fitnessapp_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=YOUR_SECURE_PASSWORD -p 5432:5432 -d postgres
    ```
    *(Note: If a container with the same name already exists, stop and remove it first: `docker stop fitnessapp-postgres && docker rm fitnessapp-postgres`)* / *(Not: Aynı isimde bir konteyner zaten varsa, önce durdurup silin)*

3.  **Configure Database Credentials / Veritabanı Kimlik Bilgilerini Yapılandırın:**
    *   Navigate to `src/main/resources/application.properties`. / `src/main/resources/application.properties` dosyasına gidin.
    *   Locate the `spring.datasource.password` property. / `spring.datasource.password` özelliğini bulun.
    *   Set its value to the **exact password** used in the `docker run` command (`YOUR_SECURE_PASSWORD`). / Değerini `docker run` komutunda kullandığınız **aynı şifre** olarak ayarlayın.
    *   Ensure `spring.datasource.username` is `postgres` (or matches the user specified in the Docker command). / `spring.datasource.username`'in `postgres` olduğundan (veya Docker komutunda belirtilen kullanıcıyla eşleştiğinden) emin olun.
    *   Save the file. / Dosyayı kaydedin.

4.  **Build and Run the Application / Uygulamayı Derleyin ve Çalıştırın:**
    ```bash
    mvn spring-boot:run
    ```
    The application will connect to the PostgreSQL container and start on port `8099` (default, check `server.port` property). / Uygulama PostgreSQL konteynerine bağlanacak ve `8099` portunda başlayacaktır.

### Access Points / Erişim Noktaları

*   **API Base URL:** `http://localhost:8099`
*   **Swagger UI (API Documentation):** `http://localhost:8099/swagger-ui.html`
*   **(Optional) PostgreSQL DB Connection:** Use a client like DBeaver or pgAdmin: / DBeaver veya pgAdmin gibi bir istemci kullanın:
    *   Host: `localhost`
    *   Port: `5432`
    *   Database: `fitnessapp_db`
    *   Username: `postgres`
    *   Password: The one you set for the Docker container. / Docker konteyneri için ayarladığınız şifre.

## 📝 API Endpoint Overview / API Endpoint Genel Bakış

Detailed request/response schemas are available via Swagger UI. / Detaylı istek/yanıt şemaları Swagger UI aracılığıyla mevcuttur.

*   `/api/users/**`: User management endpoints. / Kullanıcı yönetimi endpoint'leri.
*   `/api/exercises/**`: Exercise definition endpoints. / Egzersiz tanımlama endpoint'leri.
*   `/api/workouts/**`: Workout session management endpoints. / Antrenman seansı yönetimi endpoint'leri.
*   `/api/workouts/{workoutId}/entries`: Endpoint to add detailed exercise logs to a workout. / Bir antrenmana detaylı egzersiz kaydı ekleme endpoint'i.
