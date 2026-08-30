package com.example.ccballs

// Confirmado real (diseccionando el addon "Cobblemon Extra Balls"): PokeBallItem se
// registra como un Item comun de Minecraft. Cobblemon no necesita ningun paso de
// registro aparte para reconocerlo como pokeball -- alcanza con que el Item exista.
import com.cobblemon.mod.common.item.PokeBallItem
import com.cobblemon.mod.common.pokeball.PokeBall
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

object ModItems {

	private fun id(path: String): ResourceLocation =
		ResourceLocation.fromNamespaceAndPath(CustomCobblemonBalls.MOD_ID, path)

	// IMPORTANTE: PokeBall tiene una propiedad "item" (lateinit) que Cobblemon usa
	// internamente al procesar una captura (PokemonCapturedEvent). Es "internal" en
	// Kotlin (el compilador bloquea el acceso directo desde otro mod), asi que hay que
	// asignarla por reflexion -- mismo patron que usamos para PokeBalls.defaults en
	// PokeBallInjector.kt. Sin esto, crashea con "lateinit property item has not been
	// initialized" al capturar un Pokemon.
	private fun linkItem(pokeBall: PokeBall, item: PokeBallItem) {
		val field = PokeBall::class.java.getDeclaredField("item")
		field.isAccessible = true
		field.set(pokeBall, item)
	}

	// Notar: NO se define ningun "Item.Properties().food(...)" ni receta -> sin crafting.
	// La obtencion es solo via creative tab (ModItemGroups) o comandos/loot tables que agregues vos.

	val SIKKO_BALL: PokeBallItem = PokeBallItem(CustomPokeBalls.SIKKO_BALL).also { linkItem(CustomPokeBalls.SIKKO_BALL, it) }
	val TOURNAMENT_SIKKO_BALL: PokeBallItem = PokeBallItem(CustomPokeBalls.TOURNAMENT_SIKKO_BALL).also { linkItem(CustomPokeBalls.TOURNAMENT_SIKKO_BALL, it) }
	val GS_BALL: PokeBallItem = PokeBallItem(CustomPokeBalls.GS_BALL).also { linkItem(CustomPokeBalls.GS_BALL, it) }
	val ESCORIA_BALL: PokeBallItem = PokeBallItem(CustomPokeBalls.ESCORIA_BALL).also { linkItem(CustomPokeBalls.ESCORIA_BALL, it) }

	fun register() {
		registerItem("sikko_ball", SIKKO_BALL)
		registerItem("tournament_sikko_ball", TOURNAMENT_SIKKO_BALL)
		registerItem("gs_ball", GS_BALL)
		registerItem("escoria_ball", ESCORIA_BALL)

		CustomCobblemonBalls.LOGGER.info("Registrados 4 items de pokeball")
	}

	private fun registerItem(path: String, item: Item) {
		net.minecraft.core.Registry.register(BuiltInRegistries.ITEM, id(path), item)
	}
}
