- Escuela Bancaria y Comercial
- Licenciatura en Programación y Transformación Digital
- Materia: Aplicaciones Móviles 1
- Docente: Julio César Caro Cota
- Alumno: Cristian Rafael Ortega Cruz

INFORMACIÓN DE LA APLICACIÓN

1. Nombre y propósito de la aplicación.

    Nombre: WeatherApp
    Propósito: Mostrar datos de clima de ciudades de todo el mundo.
   
2. Cómo ejecutar la aplicación.

    Conexión a internet:
       - La aplicación hace uso de la API de OpenWeather para obtener los datos de clima de las ciudades. Es necesario que el dispositivo
         esté conectado a internet para que la aplicación funcione.
    Resolución de pantalla sugerida:
       - 1080 x 2400 px (se diseñó tomando como base un Pixel 8a y se probó también en un Xiaomi Redmi 10s, ambos con esa resolución)
    Requisitos para la búsqueda de datos derivados del uso de la API de OpenWeather:
       - No acepta todos los diacríticos: se puede usar diéresis, pero no tilde.
       - Es sensible a la corrección ortográfica con los diacríticos, aunque no son necesarios: acepta "Düssseldorf" u "Dusseldorf",
         pero no "Düsseldörf".
       - De preferencia, hay que escribir los nombres en alfabeto latino, aunque también acepta cirílico para nombres de ciudades escritos en ruso.
       - Aunque en algunos casos acepta para la misma ciudad el nombre original y el nombre en inglés ("Ciudad de México"/"Mexico City"),
         es preferible poner el nombre en el idioma original o en inglés para evitar que dé el resultado de una ciudad homónima. Por ejemplo, si se
         escribe "Nueva York" se obtiene el dato de una ciudad que se llama literalmente así, no el de New York City (NYC), New York.
   Elección de unidades:
       - Se puede elegir entre tres unidades de medida: grados Celsius (°C), grados Farehnheit (°F) y grados Kelvin (°F). Se hace mediante
         un menú desplegable al que se accede con el botón "select unit". Los grados Celsius están especificados por defecto. La elección
         se guardada en la aplicación, por lo que no hay que volver a elegir la unidad cada vez que se reabre la aplicación.

4. Descripción de los componentes principales utilizados.

   Se trata de una plicación con una estructura muy básica que hace uso de tres componentes:
       - SplashScreen: una pantalla de inicio que muestra un recurso Lottie, el nombre de la aplicación, el nombre del proyecto y un nombre de desarrollador.
       - WeatherScreen: se encarga de estructurar y mostrar el contenido de la pantalla principal. Proporciona un recurso Lottie, el cuadro de búsqueda,
         los botones para iniciar la búsqueda y cambiar la unidad de médida, junto con sus funciones auxiliares (como el convertidor de unidades o las funciones
         para guardar y recuperar la unidad de temperatura elegida entre sesiones) y datos generales de la aplicación.
       - WeatherCard: se encarga de desplegar un grupo de tarjetas en las que se muestran los datos de la ciudad elegida: nombre, temperatura, humedad
         y una descripción breve.

   Además de lo anterior, en la aplicación se usan otros archivos que son necesarios para darle funcionalidad:
       - WeatherApi: un archivo de interfaz que permite obtener los datos de OpenWeather.
       - Constants: un archivo en el que se almacenan los valores constantes de la aplicación, como la llave de la API de OpenWeather, la url del sitio de OpenWeather,
         y algunos strings que no es necesario cambiar entre distintos idiomas.
       - El archivo de recursos de cadena para evitar hacer hardcoding de varias cadenas que se muestran en la interfaz del usuario.
       - El archivo de cadenas en español para mostrar en dispositivos con esa configuración de idioma.

5. Recursos de apoyo.

   A parte de las lecciones de la clase, se hizo uso de otros recursos en línea para resolver algunos aspectos de la aplicación:
       - Conexión mediante API y estructura básica:
           - Android Knowledge. (2024, 24 de julio). The Weather App in Jetpack Compose using Kotlin [Video].
               - YouTube. https://www.youtube.com/watch?v=feG3HysJxZY&list=PLQ_Ai1O7sMV2e_xmAnZpmXWsR1xJMKlNl&index=6
       - Cambio del tipo de fuente:
           Younes, C. (2023, 24 de octubre). Use Custom Fonts in Jetpack Compose Apps! [Video]. Youtube.
               https://www.youtube.com/watch?v=vVrNmM-dtsc
       - Fuente utilizada:
           Josefin Sans: https://fonts.google.com/share?selection.family=Josefin+Sans:ital,wght@0,100..700;1,100..700
       - Agregar ícono de aplicación.
           Dino Code. (2023, 8 de julio). 02.- Cambiar icono a aplicación | Chat App Android Studio +Kotlin + Firebase [Video]. YouTube.
               https://www.youtube.com/watch?v=s8Jily_NI78&list=PLhcYacorV7U5EvHlkcoAKfBoL8zbbYOu7&index=4
       - ícono utilizado:
           Icono de Día Nublado: https://www.flaticon.es/icono-gratis/dia-nublado_1779765?term=sol+nubes&page=2&position=19&origin=search&related_id=1779765
       - Agregar resursos Lottie (lo vi antes de su clase, por eso también lo pongo):
           Dino Code. (2023, 8 de julio). 03.- Diseño de pantalla de bienvenida | Chat App Android Studio +Kotlin + Firebase [Video]. YouTube.
               https://www.youtube.com/watch?v=TPRU90yj5XY&list=PLhcYacorV7U5EvHlkcoAKfBoL8zbbYOu7&index=4
       - Recurso Lottie utilizado:
           https://app.lottiefiles.com/animation/d89959fe-f59a-413e-9122-0a5caadc0079?channel=web&source=public-animation&panel=download
       - Agregar menú desplegable:
           Caro, J. (2024, 22 de octubre). Capítulo 1: Creando Componentes Personalizados en Jetpack Compose [Video]. Loom.
               https://www.loom.com/share/644de97283bc426caf8d344cc5df59d3?sid=d232a4ed-c803-45ca-afbd-ac97597c4868
       - Agregar idiomas a la aplicación:
           Selcuk, B. (2023, 16 de abril). ADD MULTIPLE LANGUAGES IN YOUR APP - KOTLIN [Video]. YouTube. https://www.youtube.com/watch?v=ehM1JjCs9PM
