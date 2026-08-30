package com.example.ccballs

// Firma real confirmada por decompilado de Cobblemon 1.7.3 (ver diagnose.sh en el repo):
//
// PokeBall(ResourceLocation name, CatchRateModifier catchRateModifier,
//          List<CaptureEffect> effects, float waterDragValue,
//          ResourceLocation model2d, ResourceLocation model3d,
//          float throwPower, boolean ancient)
//
// MultiplierModifier(float multiplier, Function2 condition = <siempre aplica>)
// GuaranteedModifier()  -> captura 100% garantizada (la misma clase que usa la Master Ball)

import com.cobblemon.mod.common.api.pokeball.catching.CaptureEffect
import com.cobblemon.mod.common.api.pokeball.catching.CatchRateModifier
import com.cobblemon.mod.common.api.pokeball.catching.modifiers.GuaranteedModifier
import com.cobblemon.mod.common.api.pokeball.catching.modifiers.MultiplierModifier
import com.cobblemon.mod.common.pokeball.PokeBall
import net.minecraft.resources.ResourceLocation

object CustomPokeBalls {

	private fun id(path: String): ResourceLocation =
		ResourceLocation.fromNamespaceAndPath(CustomCobblemonBalls.MOD_ID, path)

	// model2d y model3d NO son nombres de textura, son referencias a archivos de modelo
	// de item (models/item/....json). Convencion confirmada diseccionando el addon real:
	//   model2d -> "item/<nombre>"        (models/item/<nombre>.json)
	//   model3d -> "item/<nombre>_model"  (models/item/<nombre>_model.json)
	private fun model2dId(path: String): ResourceLocation =
		ResourceLocation.fromNamespaceAndPath(CustomCobblemonBalls.MOD_ID, "item/$path")

	private fun model3dId(path: String): ResourceLocation =
		ResourceLocation.fromNamespaceAndPath(CustomCobblemonBalls.MOD_ID, "item/${path}_model")

	private fun ball(
		path: String,
		catchRateModifier: CatchRateModifier,
		waterDragValue: Float = 1.0F,
		throwPower: Float = 1.0F
	): PokeBall = PokeBall(
		id(path),
		catchRateModifier,
		emptyList<CaptureEffect>(),
		waterDragValue,
		model2dId(path),
		model3dId(path),
		throwPower,
		false // ancient
	)

	// SikkoBall: nivel Ultra Ball o mejor (Ultra Ball vanilla = x2.0)
	val SIKKO_BALL: PokeBall = ball("sikko_ball", MultiplierModifier(2.5F))

	// Tournament SikkoBall: captura garantizada (igual que la Master Ball)
	val TOURNAMENT_SIKKO_BALL: PokeBall = ball("tournament_sikko_ball", GuaranteedModifier())

	// GS Ball: captura garantizada (igual que la Master Ball)
	val GS_BALL: PokeBall = ball("gs_ball", GuaranteedModifier())

	// Escoria Ball: un poco peor que una Poke Ball normal (Poke Ball vanilla = x1.0)
	val ESCORIA_BALL: PokeBall = ball("escoria_ball", MultiplierModifier(0.8F))
}
