package com.example.ccballs

// Tecnica confirmada diseccionando el addon REAL "Cobblemon Extra Balls" (BallFactory.class):
// PokeBalls no tiene ningun metodo publico para agregar una ball nueva, asi que ese addon
// usa reflexion para escribir directamente en su campo privado "defaults"
// (un HashMap<ResourceLocation, PokeBall>). Una vez ahi, PokeBalls.getPokeBall(id) las
// encuentra normalmente -- que es justo lo que el cliente necesita al lanzar la ball
// (arregla el crash de SpawnPokeballPacket).

import com.cobblemon.mod.common.api.pokeball.PokeBalls
import com.cobblemon.mod.common.pokeball.PokeBall
import net.minecraft.resources.ResourceLocation

object PokeBallInjector {

	fun register() {
		try {
			val field = PokeBalls::class.java.getDeclaredField("defaults")
			field.isAccessible = true

			@Suppress("UNCHECKED_CAST")
			val defaults = field.get(PokeBalls) as HashMap<ResourceLocation, PokeBall>

			defaults[CustomPokeBalls.SIKKO_BALL.name] = CustomPokeBalls.SIKKO_BALL
			defaults[CustomPokeBalls.TOURNAMENT_SIKKO_BALL.name] = CustomPokeBalls.TOURNAMENT_SIKKO_BALL
			defaults[CustomPokeBalls.GS_BALL.name] = CustomPokeBalls.GS_BALL
			defaults[CustomPokeBalls.ESCORIA_BALL.name] = CustomPokeBalls.ESCORIA_BALL

			CustomCobblemonBalls.LOGGER.info("Inyectadas 4 pokeballs personalizadas en PokeBalls.defaults")
		} catch (e: Exception) {
			CustomCobblemonBalls.LOGGER.error("No se pudieron inyectar las pokeballs personalizadas en PokeBalls", e)
		}
	}
}
