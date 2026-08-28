#!/usr/bin/env bash
set -x

JAR=$(find ~/.gradle/caches/modules-2/files-2.1/maven.modrinth/cobblemon -name "cobblemon-*.jar" | head -n 1)
echo "Jar: $JAR"
if [ -z "$JAR" ]; then
  echo "No se encontro el jar."
  exit 0
fi

EXTRACT=/tmp/cobblemon-extract
rm -rf "$EXTRACT"
mkdir -p "$EXTRACT"
unzip -o -q "$JAR" -d "$EXTRACT"

BASE="$EXTRACT/com/cobblemon/mod/common/api/pokeball/catching/modifiers"

echo "== GuaranteedModifier (constructor) =="
javap -p "$BASE/GuaranteedModifier.class"

echo "== MultiplierModifier (constructor) =="
javap -p "$BASE/MultiplierModifier.class"

echo "== CatchRateModifiers (funciones factory) =="
javap -p "$BASE/CatchRateModifiers.class"

echo "== CatchRateModifierAdapter (bytecode, buscar strings tipo) =="
javap -c -p "$BASE/CatchRateModifierAdapter.class" | grep -i "String"

echo "== Fin diagnostico =="
