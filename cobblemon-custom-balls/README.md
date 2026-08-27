# Custom Cobblemon Balls (base de mod)

> **Flujo 100% desde la web de GitHub (sin instalar nada):** este repo incluye un
> workflow de GitHub Actions (`.github/workflows/build.yml`) que compila el mod
> automáticamente en la nube cada vez que subís un cambio. No hace falta IntelliJ,
> terminal ni Gradle local. Ver la sección "Cómo trabajar 100% desde GitHub" más abajo.

Base de addon Fabric para Cobblemon 1.21.1 que registra 4 Pokéball nuevas:

- **SikkoBall** — `sikko_ball` — x2.5 (nivel Ultra Ball o mejor; Ultra Ball vanilla = 2.0x)
- **Tournament SikkoBall** — `tournament_sikko_ball` — x100 como placeholder (nivel Master Ball, ver nota abajo)
- **GS Ball** — `gs_ball` — x100 como placeholder (nivel Master Ball) + lógica condicional pendiente
- **Escoria Ball** — `escoria_ball` — x0.5 (deliberadamente floja)

### Nota sobre "nivel Master Ball"

En Cobblemon la Master Ball no es "un multiplicador más alto": su 100% de captura es
un caso especial que salta el cálculo normal. Multiplicar por más y más se acerca a
100% pero nunca lo garantiza matemáticamente. Por eso Tournament SikkoBall y GS Ball
quedaron con un x100 de placeholder (captura casi siempre en la práctica) y no un
100% real. Para el 100% verdadero: buscá en el `PokeBalls.kt` decompilado cómo está
definida `MASTER_BALL` y copiá ese mismo mecanismo a estas dos balls (ver sección de
verificación más abajo).

Ninguna tiene receta de crafteo. Las 4 aparecen en un creative tab propio ("Custom
Cobblemon Balls").

## Cómo trabajar 100% desde GitHub (sin apps locales)

1. **Subir esta carpeta**: en tu repo de GitHub, botón "Add file" → "Upload files", y
   arrastrá todo el contenido de esta carpeta (incluida la carpeta oculta `.github/`,
   asegurate de que tu explorador de archivos muestre archivos ocultos al arrastrar, o
   subila por partes si tu navegador no arrastra carpetas ocultas).
2. **Cada vez que subís o editás algo**, andá a la pestaña **"Actions"** de tu repo. Vas
   a ver un workflow llamado "Compilar mod" corriendo automáticamente.
   - Si termina en ✅ verde: entrá a esa ejecución y bajá hasta "Artifacts" — ahí está
     el `.jar` ya compilado, listo para poner en tu carpeta `mods/` de Minecraft.
   - Si termina en ❌ rojo: hacé clic en el paso que falló para ver el error, y pegámelo
     acá para que te diga qué cambiar.
3. **Para editar código sin instalar nada**: abrí cualquier archivo `.kt` en GitHub y
   tocá el ícono de lápiz (Edit). Cambiás el texto, escribís un mensaje de commit abajo,
   y le das a "Commit changes" — eso ya dispara una nueva compilación automática.
4. **Alternativa más cómoda para editar varios archivos**: en la página principal del
   repo, apretá la tecla `.` (punto) en el teclado. Se abre un editor tipo VS Code
   directamente en el navegador (github.dev), sin instalar nada.

## Qué es 100% funcional tal cual está

- El esqueleto de mod Fabric (Gradle, `fabric.mod.json`, mod initializer)
- El registro de los 4 items y el creative tab
- El idioma (en_us / es_ar)

## Qué SÍ o SÍ tenés que revisar antes de compilar

Cobblemon no publica una API pública/estable para su sistema interno de Pokéball, así
que las clases `PokeBall`, `PokeBalls`, `PokeBallItem` y `MultiplierModifier` que uso en
`CustomPokeBalls.kt` y `ModItems.kt` son mi mejor estimación basada en cómo está
armado el mod, pero **el nombre exacto de paquete/función puede variar** según la
versión de Cobblemon que tengas. Antes de compilar:

1. Abrí el proyecto en IntelliJ IDEA con el plugin de Kotlin y Loom (`./gradlew idea` o
   importar como proyecto Gradle).
2. Corré `./gradlew genSources` (o el equivalente que ofrezca Loom) para tener las
   fuentes de Cobblemon decompiladas y navegables.
3. Con `Ctrl+N` / `Cmd+O` buscá las clases `PokeBall`, `PokeBalls` y `PokeBallItem` y
   fijate:
   - el paquete real (puede no ser `com.cobblemon.mod.common.api.pokeball`)
   - qué parámetros pide el constructor de `PokeBall`
   - cómo se llama la función para registrar una ball nueva
4. Ajustá los `import` y las llamadas en `CustomPokeBalls.kt` y `ModItems.kt` según lo
   que encuentres. Es un ajuste de una tarde, no una reescritura completa: la
   estructura general (qué archivo hace qué) ya está.

### Si trabajás 100% desde GitHub (sin decompilar nada vos)

No hace falta que decompiles nada a mano. La forma más simple trabajando solo desde la
web es: dejá que GitHub Actions intente compilar, y si falla, copiame el error exacto
que aparece en la pestaña "Actions". Con ese error puedo saber qué nombre de
clase/función real hay que usar y te paso el cambio para que lo pegues en el editor de
GitHub. Puede llevar 2-3 intentos hasta que compile del todo, es normal.

Ver los comentarios dentro de `CustomPokeBalls.kt` para más detalle línea por línea.

## GS Ball y Escoria Ball: lógica condicional

Pediste que estas dos tengan comportamiento condicional (no solo un multiplicador
fijo). Eso se resuelve enganchándose al sistema de eventos de Cobblemon (el mismo
mecanismo que usan mods como "Catch Rate" para forzar capturas garantizadas). Dejé la
plantilla y los pasos exactos en `CustomCatchLogic.kt` — ahí es donde vas a escribir,
por ejemplo:

- GS Ball: bonus extra (o captura garantizada) si el Pokémon objetivo es legendario/mítico
- Escoria Ball: multiplicador aún más bajo, o directamente sin poder capturar salvo excepciones

## Texturas y modelos (tu parte)

Dejé carpetas con un `README.txt` en cada una explicando qué archivo va dónde:

- `assets/ccballs/textures/item/` → ícono 2D del inventario
- `assets/ccballs/models/item/` → modelo JSON del ícono (parent `item/generated`)
- `assets/ccballs/bedrock/models/item/` → modelo 3D (Bedrock/GeckoLib) de la ball en el mundo
- `assets/ccballs/bedrock/textures/item/` → textura del modelo 3D
- `assets/ccballs/textures/gui/sprites/pokeball/` → sprite de la barra lateral/HUD de captura

## Obtención de las balls

Como no tienen receta, por ahora solo se consiguen desde el creative tab del mod.
Si más adelante querés loot tables, comandos de entrega, o un trade con villager,
avisame y lo sumamos.
