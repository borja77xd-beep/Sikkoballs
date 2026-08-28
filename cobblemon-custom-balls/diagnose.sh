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

echo "== Buscando clases que mencionan registerType =="
CANDIDATES=$(grep -rl "registerType" "$EXTRACT" --include="*.class" 2>/dev/null)
echo "$CANDIDATES"

echo "== Contexto de cada uso de registerType =="
for f in $CANDIDATES; do
  echo "--- $f ---"
  javap -c -p "$f" 2>/dev/null | grep -B3 "registerType"
done

echo "== Buscando donde se instancia/usa MultiplierModifier o GuaranteedModifier junto a un string =="
CANDIDATES2=$(grep -rl "MultiplierModifier\|GuaranteedModifier" "$EXTRACT" --include="*.class" 2>/dev/null | grep -vi "modifiers/MultiplierModifier.class\|modifiers/GuaranteedModifier.class\|DynamicMultiplierModifier.class")
echo "$CANDIDATES2"

echo "== Fin diagnostico =="
