# Unit tests and code coverage

Mockito segítségével JUnit teszteket készítettem, melyekkel a főbb funkciókat teszteltem.
Először problémába ütköztem a mockito importálása során, ezt úgy sikerült megoldani, hogy kezdtem egy teljesen új maven projectet, és annak a src mappájába másoltam
a játék java file-ait. Junit Jupitert (JUnit 5 használtam)

A mockitoval mockolni tudtam függőségeket, ezzel egyszerűsítve a tesztelést. Getter és setter függvényeket nem teszteltem, akkor se, ha a nevükben nem szerepelt a get vagy set.
A talált hibákat jeleztem kommentben, kifejtve, hogy mi okozta a problémát.

A code coverage-et JaCoCo segítségével végeztem el. A 0.0.8-as verziót használtam. Ennek beillesztése során is problémába ütköztem, melyet a felesleges dependencyk és plug-inok kivétele után sikerült megoldani.
