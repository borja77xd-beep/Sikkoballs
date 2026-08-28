package com.example.ccballs

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object CustomCobblemonBalls : ModInitializer {

	const val MOD_ID = "ccballs"
	val LOGGER = LoggerFactory.getLogger(MOD_ID)!!

	override fun onInitialize() {
		LOGGER.info("Inicializando Custom Cobblemon Balls")

		// Registra los items. Cada uno ya es un PokeBallItem que envuelve un PokeBall
		// real (ver CustomPokeBalls.kt) -- Cobblemon no necesita ningun paso de registro
		// aparte: con que el Item exista en el registro de items alcanza (confirmado
		// diseccionando el addon real "Cobblemon Extra Balls").
		ModItems.register()

		// Creative tab propio del mod
		ModItemGroups.register()
	}
}
