# Setup build and CI feladat leírása

A build keretrendszer beüzemeléséhez Mavent használtam. Ehhez először is át kellett alakítani a projekt struktúráját, hogy megfeleljen a Maven standardoknak.

Ezután legeneráltam a pom.xml fájlt, ahol kitöltöttem a projekt adatait, SonarCloudot beüzemeltem és a projekt függőségeket hozzáadtam.

Mivel a csapat tagjai Visual Studio Code-ot használtak, ezért hozzákötöttem ezt a SonarCloudos projekthez és az ajánlott kiegészítőket is megadtam, hogy a csapattársaim
könnyen tudják telepíteni ezeket és ne maradjon el semmi.

A Continous Integration-t GitHub workflowsal valósítottam meg. Ha a mainbe pusholunk, vagy pull requestet nyitunk, akkor automatikusan lefut ez a pipeline, amely felépít egy Java-s környezetet és lefuttatja a csapattársam által írt teszteket, ezeket felviszi SonarCloudba.

A SonarCloudos issuekat megnéztük, és mivel olyan mennyiségben voltak, hogy belátható időn belül ki lehetett őket javítani így ezt a csapattársammal megcsináltuk.