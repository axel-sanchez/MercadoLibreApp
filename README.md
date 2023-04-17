# MercadoLibreApp
Este es el proyecto prueba de Mercado Libre, con esta app se puede buscar productos, acceder para ver sus detalles y abrir este mismo producto en la app propia de Mercado Libre.

# Experiencia de usuario
Este proyecto contiene las siguientes características:

* La pantalla principal donde hay un campo para ingresar lo que queremos buscar.
* La segunda pantalla donde se ve un listado de productos.
* Una vista con un producto específico con más información del mismo y del vendedor (se accede seleccionado un producto del listado de la segunda pantalla).
* La vista de este mismo producto abierta en la app oficial de Mercado Libre.
# Capturas de pantalla

<p align="center">
  <img width="270" height="555" src="https://user-images.githubusercontent.com/51034538/232633877-af4f509f-b355-402f-b8c3-8b4baad98079.jpg">
  <img width="270" height="555" src="https://user-images.githubusercontent.com/51034538/232633904-1a5d66cc-c75a-4d85-bbea-243bfe8a905f.jpg">
</p>

<p align="center">
  <img width="270" height="555" src="https://user-images.githubusercontent.com/51034538/232633957-e33068fc-681a-43ab-a7a4-a4eb73349da9.jpg">
  <img width="270" height="555" src="https://user-images.githubusercontent.com/51034538/232633963-f64aefb5-ed8f-4ca7-87c0-4aa5e4d21bb6.jpg">
</p>

## Guía de implementación
Traigo la información desde el servicio https://api.mercadolibre.com/sites/MLA/search

### Arquitectura
Este proyecto implementa el patrón de arquitectura MVVM y sigue buenas prácticas de Clean Architecture para hacer un código más independiente, mantenible y sencillo.

#### Capas
* Presentation: Fragments y Activities
* Data: contiene la implementación del repositorio y los sources donde se conecta con la api y con la base de datos
* Domain: contiene los casos de uso y la definición del repositorio
Este proyecto usa ViewModel para almacenar y manejar datos, así como comunicar cambios hacia la vista.

### Administrador de solicitudes: Retrofit

Este proyecto utiliza Retrofit para mostrar los productos desde una API.

### Inyección de dependencia - Dagger

Este proyecto utiliza Dagger para gestionar la inyección de dependencia.

### Persistencia de datos - Room

Este proyecto utiliza la base de datos de Room para almacenar los productos.

### Testing

La app posee tests hechos con JUnit4 y Espresso

### Patrones de diseño

Utilizo algunos patrones de diseño como Observer, Singleton, Builder

# Guía de instalación
En caso de no tener instalado Android Studio, descargue la última versión estable. Una vez que tenemos el programa instalado vamos a Get from Version Control y vamos a pegar https://github.com/axel-sanchez/MercadoLibreApp.git Una vez hecho eso se va a clonar el proyecto, lo que resta sería conectar un celular y darle al botón verde de Run 'app'
