# SonarCloud beüzemelése és kódminőség javtás feladatának leírása

A BME-MIT-IET szervezeten belül a saját repónkat kiválasztva a Maven GitHub Action analízis módszerét válaszottam. Ezután az oldal által kért titok (Repository secret) hozzá lett adva a projekthez és az ott írt módosításokat átvezettem a pom.xml és build.yml konfigurációs fájlokba.

VS Code-ban a SonarLint bővítményt letöltve az analízis által talált problémákat kijavítottam, ezek között voltak: metódusok átnevezése, más adattípusok használata, bonyolult függvények szétbontása több egyszerűbbre, lambdák bevezetése.
