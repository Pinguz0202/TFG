# 📚 Biblioteca Digital📱

**Proyecto Final del Ciclo Formativo de Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM)**

**Alumno:** Álvaro Rodríguez Gamero

---

### **Índice**

-   [Introducción](#introducción)
    -   [Descripción del Proyecto](#descripción-del-proyecto)
    -   [Justificación](#justificación)
    -   [Objetivos](#objetivos)
    -   [Motivación](#motivación)
-   [Funcionalidades del Proyecto](#funcionalidades-del-proyecto)
-   [Tecnologías Utilizadas](#tecnologías-utilizadas)
-   [Guía de Instalación](#guía-de-instalación)
    -   [Pre-requisitos](#pre-requisitos)
    -   [Clonar el Repositorio](#clonar-el-repositorio)
    -   [Instalación de la API](#instalación-de-la-api)
    -   [Instalación de la Aplicación Android](#instalación-de-la-aplicación-android)
-   [Guía de Uso](#guía-de-uso)
    -   [Uso de la Aplicación Android](#uso-de-la-aplicación-android)
    -   [Endpoints de la API](#endpoints-de-la-api)
-   [Enlace a la Documentación Completa](#enlace-a-la-documentación-completa)
-   [Enlace a Figma de la Interfaz](#enlace-a-figma-de-la-interfaz)
-   [Conclusión](#conclusión)
-   [Contribuciones, Agradecimientos y Referencias](#contribuciones-agradecimientos-y-referencias)
-   [Licencias](#licencias)
-   [Contacto](#contacto)

---

### **Introducción**

#### **Descripción del Proyecto**

Este proyecto, **Biblioteca Digital**, es una solución integral para la gestión y acceso a recursos bibliográficos, compuesta por una **API robusta** y una **aplicación móvil nativa para Android**. Su objetivo principal es modernizar la experiencia de usuario en el acceso a la literatura, permitiendo a los usuarios explorar, gestionar libros y reseñas de manera intuitiva y eficiente desde sus dispositivos móviles.

#### **Justificación**

En la era digital actual, el acceso a la información y el contenido se ha vuelto primordial. La necesidad de una plataforma centralizada que facilite la organización de vastas colecciones de libros y, al mismo tiempo, ofrezca una experiencia de usuario fluida desde un dispositivo móvil, es evidente. Este proyecto busca llenar ese vacío, proporcionando una herramienta práctica para estudiantes, lectores y entusiastas de los libros.

#### **Objetivos**

* Desarrollar una **API RESTful** que sirva como columna vertebral para la gestión de datos de libros, usuarios y reseñas, asegurando la **integridad y seguridad** de la información.
* Crear una **aplicación móvil Android** con una interfaz de usuario atractiva y **fácil de usar**, que permita la interacción completa con la API.
* Implementar mecanismos de **autenticación y autorización** para proteger el acceso a funcionalidades específicas.
* Garantizar la **persistencia de datos** mediante una base de datos relacional robusta.
* Facilitar el descubrimiento de libros, así como la interacción social mediante reseñas.

#### **Motivación**

La motivación principal detrás de este proyecto radica en la pasión por la lectura y la creencia en el poder de la tecnología para hacer el conocimiento más accesible. Personalmente, busqué crear una solución que reflejara los desafíos y las oportunidades en el desarrollo de aplicaciones modernas, integrando tanto el *backend* como el *frontend* en un TFG coherente y funcional.

---

### **Funcionalidades del Proyecto**

**API (Backend):**

* **Gestión de Usuarios:** Registro, inicio de sesión (autenticación) y gestión de perfiles (autorización).
* **Gestión de Libros:** CRUD (Crear, Leer, Actualizar, Eliminar) de información de libros (título, autor, ISBN, género, etc.).
* **Gestión de Reseñas:** CRUD de reseñas y calificaciones de libros por parte de los usuarios.
* **Integridad de Datos:** Implementación de índices, funciones/procedimientos/triggers en la base de datos para asegurar la consistencia.

**Aplicación Android (Frontend):**

* **Inicio de Sesión y Registro:** Acceso seguro a la aplicación.
* **Exploración de Libros:** Listado de libros disponibles con opciones.
* **Detalle del Libro:** Visualización de información completa de cada libro y sus reseñas.
* **Funcionalidad de Reseñas:** Posibilidad de leer, escribir y editar reseñas.
* **Gestión de Perfil:** Visualización.
* **Interfaz de Usuario Intuitiva:** Diseño centrado en la usabilidad y la experiencia del usuario.

---

### **Tecnologías Utilizadas**

**Backend (API):**

* **Lenguaje de Programación:** Java
* **Framework:** Spring Boot
* **Gestión de Dependencias:** Maven
* **Base de Datos:** MySQL
* **ORM/JPA:** Hibernate (implementación de JPA)
* **Seguridad:** Spring Security / JWT
* **Herramientas:** Postman (para pruebas de API)

**Frontend (Aplicación Android):**

* **Lenguaje de Programación:** Kotlin
* **IDE:** Android Studio
* **Frameworks/Librerías:**
    * **Jetpack Compose:** Para la construcción declarativa de la interfaz de usuario (UI).
    * **Retrofit / OkHttp:** Para la comunicación eficiente y segura con la API REST (peticiones HTTP).
    * **Coil:** Para la carga asíncrona y visualización de imágenes.
    * **Dagger Hilt:** Para la inyección de dependencias, facilitando la modularidad y el testeo del código.
    * **Android Jetpack Navigation Component:** Para gestionar la navegación entre las diferentes pantallas de la aplicación.
    * **DataStore Preferences:** Para el almacenamiento de datos clave-valor de forma persistente y asíncrona.
    * **JWT Decode (Auth0):** Para decodificar y trabajar con JSON Web Tokens (JWTs) recibidos de la API.
    * **Lifecycle KTX:** Para la gestión del ciclo de vida de los componentes Android.
    * **Material Design 3:** Para componentes de UI que siguen las guías de diseño modernas de Google.
* **Gestión de Dependencias:** Gradle

---

### **Guía de Instalación**

#### **Pre-requisitos**

Asegúrate de tener instalados los siguientes programas en tu sistema:

* **Git:** [Enlace de descarga de Git](https://git-scm.com/downloads)
* **Java Development Kit (JDK) 17+:** [Enlace de descarga de OpenJDK](https://openjdk.org/install/)
* **Maven:** [Enlace de descarga de Maven](https://maven.apache.org/download.cgi)
* **MySQL Server:** Asegúrate de tener instalado y configurado tu servidor de base de datos MySQL. Puedes descargarlo desde [aquí](https://dev.mysql.com/downloads/mysql/).
* **MySQL Workbench:** Una herramienta visual para la gestión y visualización de bases de datos MySQL. Puedes descargarlo desde [aquí](https://dev.mysql.com/downloads/workbench/).
* **Android Studio:** [Enlace de descarga de Android Studio](https://developer.android.com/studio)
* **Visual Studio Code (o tu IDE preferido para la API):** [Enlace de descarga de VS Code](https://code.visualstudio.com/download)

#### **Clonar el Repositorio**

1.  Abre tu terminal (CMD, PowerShell o Git Bash).
2.  Navega a la carpeta donde quieras clonar el proyecto:
    ```bash
    cd C:\Users\{Ruta que Elijas}\
    ```
3.  Clona el repositorio:
    ```bash
    git clone [https://github.com/Pinguz0202/TFG.git](https://github.com/Pinguz0202/TFG.git)
    cd TFG
    ```

#### **Instalación de la API**

1.  Navega a la carpeta de la API:
    ```bash
    cd C:\Users\{Ruta que elijas}\TFG\api
    ```
2.  Instala las dependencias y compila el proyecto:
    ```bash
    mvn clean install
    ```
3.  Configura la conexión a la base de datos en `src/main/resources/application.properties`. Asegúrate de crear la base de datos y un usuario con los permisos adecuados.
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/[nombre_tu_bd]?useSSL=false&serverTimezone=UTC
    spring.datasource.username=[tu_usuario_bd]
    spring.datasource.password=[tu_password_bd]
    spring.jpa.hibernate.ddl-auto=update # O 'create' la primera vez
    # Otros parámetros de configuración de Spring Boot
    ```
    **Nota:** Si tienes un script SQL para crear el esquema y/o datos iniciales, menciónalo aquí y dónde se encuentra (ej., `src/main/resources/schema.sql`).
4.  Ejecuta la aplicación API:
    ```bash
    mvn spring-boot:run
    ```
    La API estará accesible en `http://localhost:8080` (o el puerto configurado).

#### **Instalación de la Aplicación Android**

1.  Abre Android Studio.
2.  Selecciona `File` > `Open...`.
3.  Navega y selecciona la carpeta `C:\Users\{Ruta que elijas}\TFG\android_app`. Android Studio debería importar el proyecto automáticamente.
4.  Espera a que Gradle sincronice las dependencias del proyecto.
5.  Configura la URL base de tu API en el código de la aplicación (ej., en `app/src/main/java/.../Config.kt` o similar). Si la API está corriendo en `localhost` desde tu PC, la app Android necesitará apuntar a la IP de tu PC o a la IP del emulador si usas uno (ej., `http://10.0.2.2:8080` para emuladores Android).
6.  Conecta un dispositivo Android (con la depuración USB activada) o inicia un emulador.
7.  Haz clic en el botón `Run` (el triángulo verde) en Android Studio para desplegar e iniciar la aplicación.

---

### **Guía de Uso**

#### **Uso de la Aplicación Android**

1.  Al iniciar la aplicación, serás dirigido a la pantalla de **inicio de sesión o registro**. Si es tu primera vez, crea una nueva cuenta.
2.  Una vez autenticado, accederás a la **pantalla principal**, donde podrás ver un listado de libros.
3.  Haz clic en un libro para ver su **detalle**, incluyendo información completa y reseñas de otros usuarios.
4.  Desde la pantalla de detalle, podrás **añadir tu propia reseña** o editar una existente si eres el autor.
5.  Explora las diferentes secciones de la aplicación para gestionar tu perfil y otras funcionalidades.

#### **Endpoints de la API**

Puedes probar la API usando herramientas como Postman o directamente desde tu navegador para los endpoints GET.

* **Base URL:** `http://localhost:8080/api/` (Nota: la versión `v1/` no se observa en tus `@RequestMapping` actuales, solo `/api/`)

| Endpoint                  | Método   | Descripción                                            | Requiere Auth | Cuerpo/Parámetros                                             |
| :------------------------ | :------- | :----------------------------------------------------- | :------------ | :------------------------------------------------------------ |
| `/auth/login`             | `POST`   | Iniciar sesión y obtener token JWT                     | No            | `{"email": "tu@email.com", "contraseña": "tu_contraseña"}`  |
| `/auth/register`          | `POST`   | Registrar un nuevo usuario                             | No            | `{"nombre": "Tu Nombre", "email": "tu@email.com", "contraseña": "tu_contraseña"}` |
| `/libros`                 | `GET`    | Obtener todos los libros                               | No            | -                                                             |
| `/libros/{isbn}`          | `GET`    | Obtener un libro por su ISBN                           | No            | -                                                             |
| `/libros`                 | `POST`   | Crear un nuevo libro                                   | Sí (ADMIN)    | `{ "isbn": "...", "titulo": "...", "autor": "...", "aniodepublicacion": 2024, "genero": "...", "sinopsis": "...", "puntuacionPromedio": 0.0 }` |
| `/libros/{isbn}`          | `PUT`    | Actualizar un libro por su ISBN                        | Sí (ADMIN)    | `{ "titulo": "...", "autor": "...", "aniodepublicacion": 2024, "genero": "...", "sinopsis": "..." }` |
| `/libros/{isbn}`          | `DELETE` | Eliminar un libro por su ISBN                          | Sí (ADMIN)    | -                                                             |
| `/libros/debug`           | `POST`   | (DEBUG) Inserta un libro de prueba                     | No            | -                                                             |
| `/favoritos`              | `GET`    | Obtener todos los libros favoritos del usuario         | Sí (USER)     | -                                                             |
| `/favoritos/{isbn}`       | `POST`   | Añadir un libro a la lista de favoritos                | Sí (USER)     | -                                                             |
| `/favoritos/{isbn}`       | `DELETE` | Eliminar un libro de la lista de favoritos             | Sí (USER)     | -                                                             |
| `/favoritos/{isbn}/check` | `GET`    | Comprobar si un libro es favorito del usuario          | Sí (USER)     | -                                                             |
| `/resenas`                | `POST`   | Crear o actualizar una reseña para un libro            | Sí (USER)     | `{"isbnLibro": "...", "puntuacion": 1-5, "comentario": "..."}` |
| `/resenas/libro/{isbn}`   | `GET`    | Obtener todas las reseñas de un libro específico      | No            | -                                                             |
| `/resenas/usuario`        | `GET`    | Obtener todas las reseñas del usuario autenticado      | Sí (USER)     | -                                                             |
| `/resenas/{id}`           | `PUT`    | Actualizar una reseña existente por su ID              | Sí (USER)     | `{"isbnLibro": "...", "puntuacion": 1-5, "comentario": "..."}` |
| `/resenas/{id}`           | `DELETE` | Eliminar una reseña por su ID (solo el propietario)    | Sí (USER)     | -                                                             |
| `/usuarios`               | `GET`    | Obtener todos los usuarios                             | Sí (ADMIN)    | -                                                             |
| `/usuarios/me`            | `GET`    | Obtener los datos del perfil del usuario autenticado   | Sí (USER)     | -                                                             |
| `/usuarios/{id}/rol`      | `PUT`    | Cambiar el rol de un usuario (ADMIN o USER) por ID     | Sí (ADMIN)    | `{"rol": "ADMIN"}` o `{"rol": "USER"}`                      |

---

### **Enlace a la Documentación Completa**

Para una visión más detallada del diseño, arquitectura y casos de prueba del proyecto, consulta la documentación completa aquí:

**[\[Enlace a tu Documentacion\]](https://leather-soybean-630.notion.site/Documentaci-n-T-cnica-BibliotecaGo-20ad2b8dd90880ffa4d0cb625c953f86)**
---

### **Enlace a Figma de la Interfaz**

Puedes explorar el diseño de la interfaz de usuario de la aplicación Android en Figma a través de este enlace:

**[[Enlace a tu Proyecto Figma](https://www.figma.com/design/LyI5Pgr3QTWRlM4ITLXoBp/TFG?node-id=0-1&p=f)]**

---

### **Conclusión**

El proyecto Biblioteca Digital ha sido un desafío enriquecedor que ha permitido integrar conocimientos de desarrollo backend y frontend, bases de datos y principios de usabilidad. Se ha logrado construir una plataforma funcional que demuestra las capacidades adquiridas durante el ciclo de DAM, con una base sólida para futuras expansiones y mejoras.

---

### **Contribuciones, Agradecimientos y Referencias**

- **Alumno:** Álvaro Rodríguez
- **Tutor/Profesor:** Jorge Juan Muñoz Morera

#### **Agradecimientos**
- A mi familia y amigos por su apoyo constante durante el desarrollo del proyecto.
- A mi tutor por su orientación y disponibilidad para resolver dudas.
- A la comunidad de desarrolladores por compartir conocimiento técnico esencial para superar retos durante la implementación.

#### **Referencias**
- [Documentación oficial de Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Documentación oficial de Kotlin](https://kotlinlang.org/docs/home.html)
- [Documentación oficial de Jetpack Compose](https://developer.android.com/jetpack/compose/documentation)
- [Documentación de Retrofit](https://square.github.io/retrofit/)
- [Documentación de Hilt (Inyección de dependencias)](https://developer.android.com/training/dependency-injection/hilt-android)
- [Iconos Material Design](https://fonts.google.com/icons)
- Consultas y ejemplos encontrados en [Stack Overflow](https://stackoverflow.com/) y repositorios de [GitHub Discussions](https://github.com/).

---

### **Licencias**

Este proyecto se distribuye bajo la licencia **MIT License**.
Puedes encontrar más detalles en el archivo `LICENSE.md` incluido en la raíz del repositorio.

---

### **Contacto**

Para cualquier pregunta o comentario sobre el proyecto, no dudes en contactarme:

* **Email:** alvarorg2003@gmail.com