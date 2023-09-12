## MANUAL DE USUARIO

https://youtu.be/BBFx4tJvmck

## BASE DE DATOS
Primero partiremos con la creación de la Base de Datos y Tablas en MySQL

### Creación de la Base de Datos

Se ha creado una base de datos denominada "FARMACIA" para albergar toda la información relacionada con la gestión de la farmacia.

![image](https://github.com/Paulesh5/Farmacia/assets/139184732/143d3c79-d33c-4316-afee-e854f81db369)
 
### Tabla de Administradores

La tabla "ADMINISTRADOR" se ha diseñado para almacenar datos sobre los administradores de la farmacia. Cada administrador se identifica mediante un nombre de usuario único y se almacena su contraseña asociada.

![image](https://github.com/Paulesh5/Farmacia/assets/139184732/89bfdbef-aa7a-43a3-8988-7177cd5ac9b1)


### Tabla de Cajeros

La tabla "CAJERO" se utiliza para almacenar información detallada sobre los cajeros de la farmacia, incluyendo su nombre, apellido, cédula de identidad, edad, correo electrónico y contraseña.

![image](https://github.com/Paulesh5/Farmacia/assets/139184732/192815eb-6e96-4b50-b338-3f829df6209f)


## Tabla de Productos

La tabla "PRODUCTOS" almacena datos relacionados con los productos disponibles en la farmacia. Cada producto se identifica mediante un código único, y se registran su nombre, precio por unidad y la cantidad disponible en el stock.

![image](https://github.com/Paulesh5/Farmacia/assets/139184732/f3aaaac7-0e16-461f-ae33-6b060b868cb5)


## Funcionalidad

El sistema de base de datos creado permite:

Gestión de Administradores: Los administradores pueden iniciar sesión en el sistema utilizando su nombre de usuario y contraseña, lo que les brinda acceso a funcionalidades de gestión.

Registro de Cajeros: Los cajeros pueden ser registrados en el sistema con sus detalles personales, lo que facilita su gestión y seguimiento.

Registro de Productos: Se pueden registrar productos en la farmacia con información detallada, incluyendo nombre, precio por unidad y la cantidad disponible en el stock.

Visualización de Productos: Se proporciona la capacidad de ver todos los productos disponibles en el sistema.

Este sistema de base de datos facilita la gestión eficiente de una farmacia al mantener un registro organizado de administradores, cajeros y productos. Además, permite la consulta de información de productos disponible en el inventario.



Primer Panel de Bienvenida:
Al ejecutar nuestro proyecto, se desplegara una primera ventana de bienvenida con un botón el cual daremos la indicación de que queremos ejecutar su funcionamiento.

![image](https://github.com/Paulesh5/Farmacia/assets/139184732/15d1c6e9-7e1f-4444-949d-7ea9b0faebe5)

Al darle click en su unico boton para comenzar. Se desplegará un Loggin el cual nos dejara ingresar nuestras credenciales.

![image](https://github.com/Paulesh5/Farmacia/assets/139184732/8659bc4f-8067-4708-9efb-7b0d8545c6dc)

Este contendrá un apartado el cual nos permita seleccionar que tipo de Usuario queremos ejecutar, entre sus opciones se encuentran "Administrador" y "Cajero".

En el momento de ingresar las credenciales correctas y dar click en "Iniciar Sesión"
Predeterminadamente se mostrará este panel:
La que corresponde a la pestaña de "Productos"

![image](https://github.com/Paulesh5/Farmacia/assets/139184732/fd12ff79-2224-426c-a10d-e3e54547cb9d)

El cual nos brinda un sistema crud muy intuitivo que nos permitirá gestionar los datos mediantes los campos de Código, Nombre, Precio y Stock. Acorde a lo que nosotros ingresemos, nosotros decidiremos si queremos que se limpie esa informacion, o si querenmos buscarla, o quiza tan solo eliminarla e incluso actualizarla o añadir.
Cabe recalcar que cualquier tarea de su funcionamiento se verá reflejado a tiempo real en el recuadro de la derecha.

![image](https://github.com/Paulesh5/Farmacia/assets/139184732/26bc8f9c-2d8b-4da1-a3eb-afe3417db9a7)

Tal y como podemos observar, hemos agregado un producto a esta tabla y este proceso se reflejará en la tabla. Tambien cuenta con un botón de cerrar sesión

Con respecto a la pestaña "Usuarios".

![image](https://github.com/Paulesh5/Farmacia/assets/139184732/a7d9080a-e994-45f4-8e44-1156798011bd)

Nosotros aqui podremos hacer busquedas mediante uno de los campos con la que se maneja nuestra tabla.
Tambien, al igual que la anterior pestaña, nos permite gestionar los registros mediante funciones tales como limpiar, crear, eliminar y actualizar.
Acompañado tambien con el botón de "cerrar sesión"


Ahora que sucede cuando le doy click en cerrar sesión.
Primeramente se desplegara una ventana que advierta de nuestra acción.

![image](https://github.com/Paulesh5/Farmacia/assets/139184732/b104b1e3-c181-4c6f-8a55-e669ec798fd2)

y cuando confirmemos nuestra petición, automaticamente volveremos al Login.

Como podrán ver, ya pusimos a prueba el usuario de "Administrador", y es momento de añadir un usuario mas desde el usuario actual, para poder ingresar como un Cajero.
Para esto simplemente iremos a la pestaña de usuarios y llenaremos las credenciales del futuro usuario.

![image](https://github.com/Paulesh5/Farmacia/assets/139184732/da94240a-6571-4f0c-a740-e0a4b310aadf)

Una vez creada, nosotros ya podremos darle click en "crear" y podremos observar que ya se creo un nuevi usuario.
Automaticamente nos enseñará esta ventana:

![image](https://github.com/Paulesh5/Farmacia/assets/139184732/8fae2288-b1b8-475b-a4ce-bf352e374c12)

y Mediante esas credenciales podrás iniciar sesión desde cajero.

![image](https://github.com/Paulesh5/Farmacia/assets/139184732/6b264c93-36c4-4635-9cc9-cef5317b9488)

Una vez que iniciemos sesión desde cajero, nosotros podremos observar la interfaz con la cual nosotros haremos registros o modificaciones

![image](https://github.com/Paulesh5/Farmacia/assets/139184732/f6bb176a-985d-431d-92a8-4fe795383e29)



En este panel contaremos con dos recuadros, pero el derecho unicamente es la vista previa de los productos disponibles y la izquierda va toda la informacion catalogada por campos como Cod Prod, Producto, Cantidad, P.V.P, Subtotal. Debido a que esta sera la información de lo que irá en los detalles de la factura
Tambien tenemos a disposición un botón para crear a partir de los datos registrados un pdf.







